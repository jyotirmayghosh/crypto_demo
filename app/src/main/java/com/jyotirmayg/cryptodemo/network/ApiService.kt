package com.jyotirmayg.cryptodemo.network

import com.jyotirmayg.cryptodemo.data.apiModel.CryptoDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("sapi/v1/tickers/24hr")
    suspend fun exchangeInfo(): Response<ArrayList<CryptoDetailResponse>>

    @GET("sapi/v1/ticker/24hr")
    suspend fun indSymbol(@Query("symbol") symbol: String): Response<CryptoDetailResponse>
}