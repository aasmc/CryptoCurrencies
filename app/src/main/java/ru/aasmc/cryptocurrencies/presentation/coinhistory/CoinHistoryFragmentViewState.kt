package ru.aasmc.cryptocurrencies.presentation.coinhistory

import androidx.annotation.DrawableRes
import ru.aasmc.cryptocurrencies.R
import ru.aasmc.cryptocurrencies.presentation.coinhistory.model.UiCoinHistory

data class CoinHistoryFragmentViewState(
    val minYAxisValue: Float = 0f,
    val coinHistory: UiCoinHistory = UiCoinHistory(),
    @DrawableRes val chartGradient: Int = R.drawable.chart_gradient_price_increase
)