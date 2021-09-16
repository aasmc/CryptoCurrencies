package ru.aasmc.cryptocurrencies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.aasmc.cryptocurrencies.domain.repositories.CoinsRepository

object CoinsSharedViewModelFactory : ViewModelProvider.Factory {
    private lateinit var repository: CoinsRepository

    fun inject(repository: CoinsRepository) {
        CoinsSharedViewModelFactory.repository = repository
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CoinsSharedViewModel() as T
    }
}