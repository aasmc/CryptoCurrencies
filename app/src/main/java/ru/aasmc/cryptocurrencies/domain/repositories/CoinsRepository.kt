package ru.aasmc.cryptocurrencies.domain.repositories

import ru.aasmc.cryptocurrencies.domain.Result
import ru.aasmc.cryptocurrencies.domain.model.Coin
import ru.aasmc.cryptocurrencies.domain.model.CoinHistory

interface CoinsRepository {
    suspend fun getCoins(): Result<List<Coin>>
    suspend fun getCoinHistory(coinId: String): Result<CoinHistory>
}