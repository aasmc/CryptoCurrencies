package ru.aasmc.cryptocurrencies.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinResponse(
    val id: String?,
    val rank: String?,
    val symbol: String?,
    val name: String?,
    val supply: String?,
    val maxSupply: String?,
    val marketCapUsd: String?,
    val volumeUsd24hr: String?,
    val priceUsd: String?,
    val changePriceUsd: String?,
    val vwap24hr: String?,
)