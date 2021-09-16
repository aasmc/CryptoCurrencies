package ru.aasmc.cryptocurrencies.presentation.coinhistory

sealed class CoinHistoryFragmentViewEffects {
    data class Failure(val cause: Throwable): CoinHistoryFragmentViewEffects()
}