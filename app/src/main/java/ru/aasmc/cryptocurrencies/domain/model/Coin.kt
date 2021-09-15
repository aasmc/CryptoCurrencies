package ru.aasmc.cryptocurrencies.domain.model

import ru.aasmc.cryptocurrencies.domain.Result
import java.math.BigDecimal
import java.util.*

class Coin private constructor(
    val id: String,
    val symbol: String,
    val name: String,
    val supply: BigDecimal,
    val marketCapUsd: BigDecimal,
    val priceUsd: BigDecimal,
    val changePercent24Hr: Float,
    val image: String
) {
    companion object {
        const val EMPTY_CHANGE_PERCENT = -1f
        val EMPTY_MARKET_CAP = BigDecimal.ZERO
        val EMPTY_SUPPLY = BigDecimal.ZERO

        private const val IMAGES_ENDPOINT = "https://static.coincap.io/assets/icons/"
        private const val IMAGES_SUFFIX = "@2x.png"

        fun of(
            id: String?,
            symbol: String?,
            name: String?,
            supply: String?,
            marketCapUsd: String?,
            pricesUsd: String?,
            changePercent24Hr: String?
        ): Result<Coin> {
            return Result {
                requireNotNull(id)
                require(id.isNotBlank())

                requireNotNull(symbol)
                require(symbol.isNotBlank())

                requireNotNull(name)
                require(name.isNotBlank())

                requireNotNull(pricesUsd)
                require(pricesUsd.isNotBlank())

                val image = "$IMAGES_ENDPOINT${symbol.lowercase(Locale.getDefault())}$IMAGES_SUFFIX"
                Coin(
                    id = id,
                    symbol = symbol,
                    name = name,
                    supply = supply?.toBigDecimal() ?: EMPTY_SUPPLY,
                    marketCapUsd = marketCapUsd?.toBigDecimal() ?: EMPTY_MARKET_CAP,
                    priceUsd = pricesUsd.toBigDecimal(),
                    changePercent24Hr = changePercent24Hr?.toFloat() ?: EMPTY_CHANGE_PERCENT,
                    image = image
                )
            }
        }
    }
}





















