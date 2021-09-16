package ru.aasmc.cryptocurrencies.presentation.coinlist

fun interface CoinClickListener {
    fun onCoinClicked(coinId: String, name: String)
}