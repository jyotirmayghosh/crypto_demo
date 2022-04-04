package com.jyotirmayg.cryptodemo.feature.symbol

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jyotirmayg.cryptodemo.data.repository.ExchangeRepo

class SymbolViewModelFactory(
    private val repo: ExchangeRepo,
    private val app: Application
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SymbolViewModel(repo, app) as T
    }
}