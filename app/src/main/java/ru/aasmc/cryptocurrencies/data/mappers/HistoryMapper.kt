package ru.aasmc.cryptocurrencies.data.mappers

import ru.aasmc.cryptocurrencies.data.api.model.HistoryDataPointsResponse
import ru.aasmc.cryptocurrencies.domain.Result
import ru.aasmc.cryptocurrencies.domain.model.CoinHistory

class HistoryMapper {
    fun toDomain(response: List<HistoryDataPointsResponse>): Result<CoinHistory> {
        return CoinHistory.of(response.map { it.date to it.priceUsd }.toMap())
    }
}