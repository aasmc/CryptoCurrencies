package ru.aasmc.cryptocurrencies

import android.app.Application
import ru.aasmc.cryptocurrencies.data.api.CoinCapApi
import ru.aasmc.cryptocurrencies.data.mappers.CoinMapper
import ru.aasmc.cryptocurrencies.data.mappers.HistoryMapper
import ru.aasmc.cryptocurrencies.data.repositories.CoinCapCoinsRepository
import ru.aasmc.cryptocurrencies.domain.repositories.CoinsRepository
import ru.aasmc.cryptocurrencies.presentation.CoinsSharedViewModelFactory
import ru.aasmc.cryptocurrencies.presentation.coinhistory.CoinHistoryFragmentViewModelFactory
import ru.aasmc.cryptocurrencies.presentation.coinlist.CoinListFragmentViewModelFactory
import ru.aasmc.cryptocurrencies.utils.DefaultDispatchersProvider

class CryptoCurrenciesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val repo = createRepository()
        CoinsSharedViewModelFactory.inject(repo)
        CoinListFragmentViewModelFactory.inject(repo)
        CoinHistoryFragmentViewModelFactory.inject(repo)
    }

    private fun createRepository(): CoinsRepository {
        return CoinCapCoinsRepository(
            DefaultDispatchersProvider(),
            CoinMapper(),
            HistoryMapper(),
            CoinCapApi.create(this)
        )
    }
}