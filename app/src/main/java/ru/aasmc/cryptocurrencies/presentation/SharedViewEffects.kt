package ru.aasmc.cryptocurrencies.presentation

sealed class SharedViewEffects {
    data class PriceVariation(val variation: Int) : SharedViewEffects()
}