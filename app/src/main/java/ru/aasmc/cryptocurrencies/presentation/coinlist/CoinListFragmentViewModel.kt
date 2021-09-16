package ru.aasmc.cryptocurrencies.presentation.coinlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.aasmc.cryptocurrencies.domain.Result
import ru.aasmc.cryptocurrencies.domain.model.Coin
import ru.aasmc.cryptocurrencies.domain.repositories.CoinsRepository
import ru.aasmc.cryptocurrencies.presentation.coinlist.mappers.UiCoinMapper

class CoinListFragmentViewModel(
    private val coinsRepository: CoinsRepository,
    private val uiCoinMapper: UiCoinMapper
) : ViewModel() {

    private val _viewState = MutableStateFlow(CoinListFragmentViewState())
    private val _viewEffects = MutableSharedFlow<CoinListFragmentViewEffects>()

    val viewState: StateFlow<CoinListFragmentViewState> get() = _viewState.asStateFlow()
    val viewEffects: SharedFlow<CoinListFragmentViewEffects> get() = _viewEffects.asSharedFlow()

    fun requestCoins() {
        if (viewState.value.coins.isNotEmpty()) return

        viewModelScope.launch {
            when (val result = coinsRepository.getCoins()) {
                is Result.Success -> handleCoinList(result.value)
                is Result.Failure -> handleFailure(result.cause)
            }
        }
    }

    private fun handleCoinList(coins: List<Coin>) {
        val uiCoins = coins.map { uiCoinMapper.toUi(it) }

        _viewState.value = CoinListFragmentViewState(loading = false, coins = uiCoins)
    }

    private suspend fun handleFailure(cause: Throwable) {
        _viewEffects.emit(CoinListFragmentViewEffects.Failure(cause))
    }

}