package ru.aasmc.cryptocurrencies.presentation.coinlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.aasmc.cryptocurrencies.databinding.RecyclerViewCoinItemBinding
import ru.aasmc.cryptocurrencies.presentation.coinlist.CoinClickListener
import ru.aasmc.cryptocurrencies.presentation.coinlist.model.UiCoin

class CoinAdapter(
    private val listener: CoinClickListener
) : ListAdapter<UiCoin, CoinViewHolder>(CoinDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(
            binding = RecyclerViewCoinItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener = listener
        )
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}