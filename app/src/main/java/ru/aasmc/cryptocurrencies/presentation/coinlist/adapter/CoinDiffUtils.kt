package ru.aasmc.cryptocurrencies.presentation.coinlist.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.aasmc.cryptocurrencies.presentation.coinlist.model.UiCoin

class CoinDiffUtils : DiffUtil.ItemCallback<UiCoin>() {
    override fun areItemsTheSame(oldItem: UiCoin, newItem: UiCoin): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UiCoin, newItem: UiCoin): Boolean {
        return oldItem == newItem
    }

}