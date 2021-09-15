package ru.aasmc.cryptocurrencies.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinHistoryResponse(val data: List<HistoryDataPointsResponse>?)