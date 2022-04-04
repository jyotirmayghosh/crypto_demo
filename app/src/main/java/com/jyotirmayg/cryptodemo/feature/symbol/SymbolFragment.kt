package com.jyotirmayg.cryptodemo.feature.symbol

import androidx.lifecycle.ViewModelProvider
import com.jyotirmayg.cryptodemo.R
import com.jyotirmayg.cryptodemo.base.BaseFragment
import com.jyotirmayg.cryptodemo.databinding.SymbolFragmentBinding
import com.jyotirmayg.cryptodemo.feature.MainViewModel
import com.jyotirmayg.cryptodemo.feature.home.HomeFragment
import com.jyotirmayg.cryptodemo.network.Status
import com.jyotirmayg.cryptodemo.utilities.Print
import com.jyotirmayg.cryptodemo.utilities.snackbar
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SymbolFragment : BaseFragment<SymbolFragmentBinding>(), KodeinAware {

    override val kodein: Kodein by kodein()

    private val factory: SymbolViewModelFactory by instance()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: SymbolViewModel

    override fun findContentView(): Int = R.layout.symbol_fragment

    override fun onReady() {
        viewModel = ViewModelProvider(requireActivity(), factory)[SymbolViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mBinding?.let {
            it.viewmodel = mainViewModel
            it.executePendingBindings()
        } ?: throw Exception("onReady: Binding is null.")

        val symbol = arguments?.getString("symbol").toString()
        Print.d(symbol)
        viewModel.getExchangeSymbol(symbol)

        viewModel.exchangeList.observe(viewLifecycleOwner) {
            val coin = it.data
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    mBinding?.swipeRefresh?.isRefreshing = false
                    //viewModel.refreshExchangeInfoList(symbol, HomeFragment.REFRESH_DELAY_IN_MILLIS)
                    mBinding?.let {
                        it.coin = coin
                        it.executePendingBindings()
                    } ?: throw Exception("onReady: Binding is null.")
                }
                Status.ERROR -> {
                    requireView().snackbar(it.message ?: "An error occur while updating list")
                }
            }

        }

        mBinding?.swipeRefresh?.apply {
            setOnRefreshListener {
                viewModel.getExchangeSymbol(symbol)
            }
            isRefreshing = true
        }
    }
}