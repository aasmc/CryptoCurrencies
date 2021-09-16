package ru.aasmc.cryptocurrencies.presentation.coinhistory.model

import com.github.mikephil.charting.data.Entry

data class UiCoinHistory(
    val pricesOverTime: List<Entry> = emptyList(),
    val lowestValue: Float = 0f,
    val highestValue: Float = 0f
)

