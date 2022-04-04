package com.jyotirmayg.cryptodemo.feature

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jyotirmayg.cryptodemo.utilities.Print
import com.jyotirmayg.cryptodemo.utilities.numberFormat

class MainViewModel(app: Application) : ViewModel() {

    // for back navigation
    private val _navigationFlow = MutableLiveData<Navigation?>()
    val navigation: LiveData<Navigation?> = _navigationFlow

    fun applyNavigation(navigation: Navigation?){
        _navigationFlow.postValue(navigation)
    }

    fun getCurrencyFormat(rate: String?): String? {
        Print.d(rate.toString())
        return rate?.let { numberFormat(it) }
    }
}