package ru.aasmc.cryptocurrencies.presentation.coinlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.aasmc.cryptocurrencies.domain.repositories.CoinsRepository
import ru.aasmc.cryptocurrencies.presentation.coinlist.mappers.UiCoinMapper
import ru.aasmc.cryptocurrencies.utils.NumberFormatter

object CoinListFragmentViewModelFactory : ViewModelProvider.Factory {

    private lateinit var repository: CoinsRepository

    fun inject(repository: CoinsRepository) {
        CoinListFragmentViewModelFactory.repository = repository
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CoinListFragmentViewModel(
            repository,
            UiCoinMapper(NumberFormatter())
        ) as T
    }
}