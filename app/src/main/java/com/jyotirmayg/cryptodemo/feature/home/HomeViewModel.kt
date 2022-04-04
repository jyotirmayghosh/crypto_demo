package com.jyotirmayg.cryptodemo.feature.home

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
import com.jyotirmayg.cryptodemo.utilities.numberFormat
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: ExchangeRepo, val app: Application) : ViewModel() {

    private val _exchangeLiveData: MutableLiveData<Resource<ArrayList<CryptoDetailResponse>>> =
        MutableLiveData()
    val exchangeList: LiveData<Resource<ArrayList<CryptoDetailResponse>>> = _exchangeLiveData

    private val _assetLiveData: MutableLiveData<QuoteAsset> = MutableLiveData()
    val _assetType: LiveData<QuoteAsset> = _assetLiveData

    fun route(actions: QuoteAsset) {
        Print.d(actions.name)
        _assetLiveData.postValue(actions)
        /*when (actions) {
            QuoteAsset.BTC -> {

            }
            QuoteAsset.INR -> {

            }
            QuoteAsset.USDT -> {

            }
            QuoteAsset.WRX -> {

            }
        }*/
    }

    fun refreshExchangeInfoList(delayMillis: Long) {
        Handler(Looper.getMainLooper()).postDelayed({
            getExchangeInfoList()
        }, delayMillis)
    }

    fun getExchangeInfoList() {
        viewModelScope.launch {
            _exchangeLiveData.postValue(repo.loading())
            val apiResult = repo.getExchangeList()
            _exchangeLiveData.postValue(apiResult)
            if (apiResult.status == Status.SUCCESS) {
                Print.d("List fetched")
            } else {
                Print.e("Error Message: ${apiResult.message}")
            }
        }
    }

    fun getCurrencyFormat(rate: String): String? {
        return numberFormat(rate)
    }
}