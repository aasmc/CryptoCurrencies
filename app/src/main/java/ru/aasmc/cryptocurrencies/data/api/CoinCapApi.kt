package ru.aasmc.cryptocurrencies.data.api

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.aasmc.cryptocurrencies.data.api.ApiConstants.BASE_ENDPOINT
import ru.aasmc.cryptocurrencies.data.api.ApiConstants.ASSETS
import ru.aasmc.cryptocurrencies.data.api.ApiConstants.HISTORY
import ru.aasmc.cryptocurrencies.data.api.interceptors.NetworkStatusInterceptor
import ru.aasmc.cryptocurrencies.data.api.model.CoinHistoryResponse
import ru.aasmc.cryptocurrencies.data.api.model.CoinListResponse

interface CoinCapApi {

    @GET("$BASE_ENDPOINT$ASSETS")
    suspend fun getCoins(@Query("limit") limit: Int = 50): CoinListResponse

    @GET("$BASE_ENDPOINT$ASSETS/{id}/$HISTORY")
    suspend fun getCoinHistory(
        @Path("id") coinId: String,
        @Query("interval") interval: String = "m5",
        @Query("start") start: Long,
        @Query("end") end: Long
    ): CoinHistoryResponse

    companion object {
        fun create(context: Context): CoinCapApi {
            return Retrofit.Builder()
                .baseUrl(BASE_ENDPOINT)
                .client(createOkHttpClient(context))
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(CoinCapApi::class.java)
        }

        private fun createOkHttpClient(context: Context): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(NetworkStatusInterceptor(ConnectionManager(context)))
                .addInterceptor(httpLoggingInterceptor)
                .build()
        }

        private val httpLoggingInterceptor: HttpLoggingInterceptor
            get() = HttpLoggingInterceptor { message ->
                Log.i("Network", message)
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
    }

}