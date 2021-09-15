package ru.aasmc.cryptocurrencies.data.repositories

import kotlinx.coroutines.withContext
import ru.aasmc.cryptocurrencies.data.api.CoinCapApi
import ru.aasmc.cryptocurrencies.data.mappers.CoinMapper
import ru.aasmc.cryptocurrencies.data.mappers.HistoryMapper
import ru.aasmc.cryptocurrencies.domain.Result
import ru.aasmc.cryptocurrencies.domain.model.Coin
import ru.aasmc.cryptocurrencies.domain.model.CoinHistory
import ru.aasmc.cryptocurrencies.domain.repositories.CoinsRepository
import ru.aasmc.cryptocurrencies.domain.requireValue
import ru.aasmc.cryptocurrencies.utils.DispatchersProvider
import java.time.Instant

class CoinCapCoinsRepository(
    private val dispatchersProvider: DispatchersProvider,
    private val coinMapper: CoinMapper,
    private val historyMapper: HistoryMapper,
    private val coinCapApi: CoinCapApi
) : CoinsRepository {

    override suspend fun getCoins(): Result<List<Coin>> = withContext(dispatchersProvider.io()) {
        Result {
            coinCapApi.getCoins().data
                .orEmpty()
                .map { coinMapper.toDomain(it) }
                .map { it.requireValue() }
        }
    }

    override suspend fun getCoinHistory(coinId: String): Result<CoinHistory> =
        withContext(dispatchersProvider.io()) {
            Result {
                val dayInSeconds = 86400L
                val now = Instant.now()
                val aDayAgo = now.minusSeconds(dayInSeconds)
                val response = coinCapApi.getCoinHistory(
                    coinId = coinId,
                    start = aDayAgo.toEpochMilli(),
                    end = now.toEpochMilli()
                ).data.orEmpty()

                historyMapper
                    .toDomain(response)
                    .requireValue()
            }
        }
}