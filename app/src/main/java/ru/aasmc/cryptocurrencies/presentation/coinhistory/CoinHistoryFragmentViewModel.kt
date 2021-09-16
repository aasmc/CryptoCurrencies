package ru.aasmc.cryptocurrencies.presentation.coinhistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.aasmc.cryptocurrencies.R
import ru.aasmc.cryptocurrencies.domain.Result
import ru.aasmc.cryptocurrencies.domain.model.CoinHistory
import ru.aasmc.cryptocurrencies.domain.repositories.CoinsRepository
import ru.aasmc.cryptocurrencies.presentation.coinhistory.mappers.UiCoinHistoryMapper
import java.util.*

class CoinHistoryFragmentViewModel(
    private val coinsRepository: CoinsRepository,
    private val uiCoinHistoryMapper: UiCoinHistoryMapper
) : ViewModel() {

    private val _viewState = MutableStateFlow(CoinHistoryFragmentViewState())
    private val _viewEffects = MutableSharedFlow<CoinHistoryFragmentViewEffects>()

    val viewState: StateFlow<CoinHistoryFragmentViewState> = _viewState.asStateFlow()
    val viewEffects: SharedFlow<CoinHistoryFragmentViewEffects> = _viewEffects.asSharedFlow()

    fun getCoinHistory(coinId: String) {
        _viewState.value = CoinHistoryFragmentViewState()
        viewModelScope.launch {
            when (val history = coinsRepository.getCoinHistory(coinId)) {
                is Result.Success -> handleCoinHistory(history = history.value)
                is Result.Failure -> handleFailures(history.cause)
            }
        }
    }

    private fun handleCoinHistory(history: CoinHistory) {
        val pricesOverTime = history.pricesOverTime
        val firstTimeSample = Collections.min(pricesOverTime.keys)
        val lastTimeSample = Collections.max(pricesOverTime.keys)
        val firstValue = pricesOverTime.getValue(firstTimeSample)
        val lastValue = pricesOverTime.getValue(lastTimeSample)
        val uiHistory = uiCoinHistoryMapper.toUi(history)
        val lowestValue = Collections.min(pricesOverTime.values).toFloat()
        val minYAxisValue = lowestValue - (lowestValue * 0.05f)

        val newState = if (lastValue < firstValue) {
            CoinHistoryFragmentViewState(
                minYAxisValue,
                uiHistory,
                R.drawable.chart_gradient_price_decrease
            )
        } else {
            CoinHistoryFragmentViewState(minYAxisValue, uiHistory)
        }
        _viewState.value = newState
    }

    private suspend fun handleFailures(cause: Throwable) {
        _viewEffects.emit(CoinHistoryFragmentViewEffects.Failure(cause))
    }
}