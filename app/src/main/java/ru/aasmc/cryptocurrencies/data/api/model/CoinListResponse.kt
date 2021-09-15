package ru.aasmc.cryptocurrencies.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinListResponse(val data: List<CoinResponse>?)