package com.jyotirmayg.cryptodemo.feature.symbol

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyotirmayg.cryptodemo.data.apiModel.CryptoDetailResponse
import com.jyotirmayg.cryptodemo.data.repository.ExchangeRepo
import com.jyotirmayg.cryptodemo.network.Resource
import com.jyotirmayg.cryptodemo.network.Status
import com.jyotirmayg.cryptodemo.utilities.Print
import com.jyotirmayg.cryptodemo.utilities.SingleLiveEvent
import kotlinx.coroutines.launch

class SymbolViewModel(private val repo: ExchangeRepo, app: Application) : ViewModel() {

    private val _exchangeLiveData: SingleLiveEvent<Resource<CryptoDetailResponse>> = SingleLiveEvent()
    val exchangeList: LiveData<Resource<CryptoDetailResponse>> = _exchangeLiveData

    fun refreshExchangeInfoList(symbol: String, delayMillis: Long) {
        Handler(Looper.getMainLooper()).postDelayed({
            getExchangeSymbol(symbol)
        }, delayMillis)
    }

    fun getExchangeSymbol(symbol: String) {
        viewModelScope.launch {
            _exchangeLiveData.postValue(repo.loading())
            val apiResult = repo.getExchangeSymbol(symbol)
            _exchangeLiveData.postValue(apiResult)
            if (apiResult.status == Status.SUCCESS) {
                Print.d("List fetched")
            } else {
                Print.e("Error Message: ${apiResult.message}")
            }
        }
    }
}