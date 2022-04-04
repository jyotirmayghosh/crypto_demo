package com.jyotirmayg.cryptodemo.data.repository

import com.jyotirmayg.cryptodemo.base.BaseRepo
import com.jyotirmayg.cryptodemo.network.ApiService

class ExchangeRepo(private val apiService: ApiService) : BaseRepo() {

    suspend fun getExchangeList() = safeApiCall {
        apiService.exchangeInfo()
    }

    suspend fun getExchangeSymbol(symbol: String) = safeApiCall {
        apiService.indSymbol(symbol)
    }
}