package ru.aasmc.cryptocurrencies.domain.model

import ru.aasmc.cryptocurrencies.domain.Result
import java.math.BigDecimal
import java.time.Instant

class CoinHistory private constructor(
    val pricesOverTime: Map<Instant, BigDecimal>
) {
    companion object {
        fun of(mapping: Map<String?, String?>) = Result {
            CoinHistory(
                mapping
                    .filter { it.key != null && it.value != null }
                    .map { Instant.parse(it.key!!) to BigDecimal(it.value) }
                    .toMap()
            )
        }
    }
}