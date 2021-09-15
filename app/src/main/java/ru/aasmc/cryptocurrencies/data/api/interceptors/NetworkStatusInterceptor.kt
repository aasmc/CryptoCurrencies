package ru.aasmc.cryptocurrencies.data.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ru.aasmc.cryptocurrencies.data.api.ConnectionManager
import ru.aasmc.cryptocurrencies.domain.model.NetworkUnavailable

class NetworkStatusInterceptor(private val connectionManager: ConnectionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (connectionManager.isConnected) {
            chain.proceed(chain.request())
        } else {
            throw NetworkUnavailable()
        }
    }
}