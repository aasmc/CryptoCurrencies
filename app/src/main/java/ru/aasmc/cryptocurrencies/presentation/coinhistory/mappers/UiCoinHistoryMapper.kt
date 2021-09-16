package ru.aasmc.cryptocurrencies.presentation.coinhistory.mappers

import com.github.mikephil.charting.data.Entry
import ru.aasmc.cryptocurrencies.domain.model.CoinHistory
import ru.aasmc.cryptocurrencies.presentation.coinhistory.model.UiCoinHistory
import java.math.RoundingMode

class UiCoinHistoryMapper {
    fun toUi(coinHistory: CoinHistory): UiCoinHistory {
        val entries = mutableListOf<Entry>()
        var index = 0
        coinHistory.pricesOverTime
            .map {
                index++ to it.value.setScale(2, RoundingMode.CEILING).toFloat()
            }
            .forEach {
                entries.add(Entry(it.first.toFloat(), it.second))
            }
        return UiCoinHistory(
            pricesOverTime = entries,
            lowestValue = coinHistory.pricesOverTime.values.minOrNull()?.toFloat() ?: 0f,
            highestValue = coinHistory.pricesOverTime.values.maxOrNull()?.toFloat() ?: 0f
        )
    }
}