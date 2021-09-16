package ru.aasmc.cryptocurrencies.presentation.coinlist.mappers

import ru.aasmc.cryptocurrencies.domain.model.Coin
import ru.aasmc.cryptocurrencies.presentation.coinlist.model.UiCoin
import ru.aasmc.cryptocurrencies.utils.NumberFormatter

class UiCoinMapper(private val formatter: NumberFormatter) {
    companion object {
        private const val EMPTY_VALUE = "N/A"
    }

    fun toUi(coin: Coin): UiCoin {
        val supply = if (coin.supply == Coin.EMPTY_SUPPLY) {
            EMPTY_VALUE
        } else {
            formatter.compressedFormattedValueOf(coin.supply)
        }

        val marketCap = if (coin.marketCapUsd == Coin.EMPTY_MARKET_CAP) {
            EMPTY_VALUE
        } else {
            "$${formatter.compressedFormattedValueOf(coin.marketCapUsd)}"
        }

        val changePercentage = if (coin.changePercent24Hr == Coin.EMPTY_CHANGE_PERCENT) {
            EMPTY_VALUE
        } else {
            "%.2f%%".format(coin.changePercent24Hr)
        }

        return UiCoin(
            id = coin.id,
            symbol = coin.symbol,
            name = coin.name,
            supply = supply,
            marketCapUsd = marketCap,
            priceUsd = "$${formatter.extendedFormattedValueOf(coin.priceUsd)}",
            changePercent24Hr = changePercentage,
            image = coin.image
        )
    }
}




















