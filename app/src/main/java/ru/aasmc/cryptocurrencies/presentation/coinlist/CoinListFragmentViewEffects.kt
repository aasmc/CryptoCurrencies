package ru.aasmc.cryptocurrencies.presentation.coinlist

sealed class CoinListFragmentViewEffects {
    data class Failure(val cause: Throwable) : CoinListFragmentViewEffects()
}