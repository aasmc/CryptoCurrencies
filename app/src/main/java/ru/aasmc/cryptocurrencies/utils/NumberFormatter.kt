package ru.aasmc.cryptocurrencies.utils

import java.math.BigDecimal
import java.text.DecimalFormat

class NumberFormatter {

    private val suffixes = mapOf(
        1_000L to "k",
        1_000_000L to "m",
        1_000_000_000L to "b",
        1_000_000_000_000L to "t",
    )

    fun compressedFormattedValueOf(value: BigDecimal): String {
        val closest = suffixes.filterKeys { it <= value.toLong() }
        val divideBy = closest.keys.last()
        val suffix = closest.values.last()
        val truncated = value.toLong() / (divideBy / 10F)

        return "%.2f$suffix".format(truncated / 10F)
    }

    fun extendedFormattedValueOf(value: BigDecimal): String {
        // Creates a DecimalFormat using the given pattern and the symbols for the default
        // FORMAT locale. This is a convenient way to obtain a DecimalFormat
        // when internationalization is not the main concern.
        return DecimalFormat("#,###.00").format(value)
    }
}