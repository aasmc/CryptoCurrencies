package ru.aasmc.cryptocurrencies.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HistoryDataPointsResponse(
    val priceUsd: String?,
    val date: String?
)