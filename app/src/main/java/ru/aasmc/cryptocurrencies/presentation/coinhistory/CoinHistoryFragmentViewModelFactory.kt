package ru.aasmc.cryptocurrencies.presentation.coinhistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.aasmc.cryptocurrencies.domain.repositories.CoinsRepository
import ru.aasmc.cryptocurrencies.presentation.coinhistory.mappers.UiCoinHistoryMapper

object CoinHistoryFragmentViewModelFactory : ViewModelProvider.Factory {

    private lateinit var repository: CoinsRepository

    fun inject(repository: CoinsRepository) {
        CoinHistoryFragmentViewModelFactory.repository = repository
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CoinHistoryFragmentViewModel(
            repository,
            UiCoinHistoryMapper()
        ) as T
    }
}