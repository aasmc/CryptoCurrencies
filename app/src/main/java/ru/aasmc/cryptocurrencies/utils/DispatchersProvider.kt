package ru.aasmc.cryptocurrencies.utils

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {
    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
}