package ru.aasmc.cryptocurrencies.presentation.coinlist

import ru.aasmc.cryptocurrencies.presentation.coinlist.model.UiCoin

data class CoinListFragmentViewState(
    val loading: Boolean = true,
    val coins: List<UiCoin> = emptyList()
)