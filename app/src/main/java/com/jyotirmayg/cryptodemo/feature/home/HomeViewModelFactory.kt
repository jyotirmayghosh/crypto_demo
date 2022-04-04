package com.jyotirmayg.cryptodemo.feature.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jyotirmayg.cryptodemo.data.repository.ExchangeRepo

class HomeViewModelFactory(
    private val exchangeRepo: ExchangeRepo,
    private val app: Application
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(exchangeRepo, app) as T
    }
}