package ru.aasmc.cryptocurrencies.presentation.coinlist.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.aasmc.cryptocurrencies.databinding.RecyclerViewCoinItemBinding
import ru.aasmc.cryptocurrencies.presentation.coinlist.CoinClickListener
import ru.aasmc.cryptocurrencies.presentation.coinlist.model.UiCoin
import ru.aasmc.cryptocurrencies.presentation.loadImage

class CoinViewHolder(
    private val binding: RecyclerViewCoinItemBinding,
    private val listener: CoinClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(coin: UiCoin) {
        val (id, symbol, name, supply, marketCapUsd, priceUsd, changePercent24Hr, image) = coin

        with(binding) {
            coinImage.loadImage(image)
            coinName.text = name
            coinSymbol.text = symbol
            coinPrice.text = priceUsd
            coinSupply.text = supply
            coinMarketCap.text = marketCapUsd
            coinChange.text = changePercent24Hr
        }

        itemView.setOnClickListener { listener.onCoinClicked(id, name) }
    }
}