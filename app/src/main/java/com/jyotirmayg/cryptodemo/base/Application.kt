package com.jyotirmayg.cryptodemo.base

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.jyotirmayg.cryptodemo.data.repository.ExchangeRepo
import com.jyotirmayg.cryptodemo.feature.home.HomeViewModelFactory
import com.jyotirmayg.cryptodemo.feature.MainViewModelFactory
import com.jyotirmayg.cryptodemo.feature.symbol.SymbolViewModelFactory
import com.jyotirmayg.cryptodemo.network.ApiBuilder
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class Application : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@Application))

        bind() from provider { ExchangeRepo(ApiBuilder.API_SERVICE) }

        bind() from provider { MainViewModelFactory(this@Application) }
        bind() from provider { HomeViewModelFactory(instance(), this@Application) }
        bind() from provider { SymbolViewModelFactory(instance(), this@Application) }
    }
}