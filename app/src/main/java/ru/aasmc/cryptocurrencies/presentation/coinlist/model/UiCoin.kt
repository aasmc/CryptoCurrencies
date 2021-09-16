package ru.aasmc.cryptocurrencies.presentation.coinlist.model

data class UiCoin(
    val id: String,
    val symbol: String,
    val name: String,
    val supply: String,
    val marketCapUsd: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val image: String
)
