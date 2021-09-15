package ru.aasmc.cryptocurrencies.data.mappers

import ru.aasmc.cryptocurrencies.data.api.model.CoinResponse
import ru.aasmc.cryptocurrencies.domain.Result
import ru.aasmc.cryptocurrencies.domain.model.Coin

class CoinMapper {
    fun toDomain(coinResponse: CoinResponse): Result<Coin> {
        val (
            id,
            _,
            symbol,
            name,
            supply,
            _,
            marketCapUsd,
            _,
            priceUsd,
            changePercent24Hr
        ) = coinResponse

        return Coin.of(id, symbol, name, supply, marketCapUsd, priceUsd, changePercent24Hr)
    }
}