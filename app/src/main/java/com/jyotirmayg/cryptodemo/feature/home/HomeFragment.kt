package com.jyotirmayg.cryptodemo.feature.home

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.jyotirmayg.cryptodemo.R
import com.jyotirmayg.cryptodemo.base.BaseFragment
import com.jyotirmayg.cryptodemo.data.apiModel.CryptoDetailResponse
import com.jyotirmayg.cryptodemo.databinding.HomeFragmentBinding
import com.jyotirmayg.cryptodemo.feature.MainViewModel
import com.jyotirmayg.cryptodemo.feature.Navigation
import com.jyotirmayg.cryptodemo.network.Status
import com.jyotirmayg.cryptodemo.utilities.snackbar
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.ArrayList

class HomeFragment : BaseFragment<HomeFragmentBinding>(), KodeinAware {

    override val kodein: Kodein by kodein()

    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    private lateinit var mainViewModel: MainViewModel

    private var quoteAsset = QuoteAsset.INR
    private var cryptoRespArrayList = ArrayList<CryptoDetailResponse>()

    override fun findContentView(): Int = R.layout.home_fragment

    override fun onReady() {
        viewModel = ViewModelProvider(requireActivity(), factory)[HomeViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mBinding?.let {
            it.lifecycleOwner = this@HomeFragment
            it.viewmodel = viewModel
            it.quoteAsset = quoteAsset
            it.executePendingBindings()
        } ?: throw Exception("onReady: Binding is null.")

        viewModel.getExchangeInfoList()

        viewModel._assetType.observe(viewLifecycleOwner) {
            quoteAsset = it
            mBinding?.let {
                it.quoteAsset = quoteAsset
                it.executePendingBindings()
            } ?: throw Exception("onReady: Binding is null.")

            setUpAdapter()
        }

        viewModel.exchangeList.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    cryptoRespArrayList = it.data!!
                    mBinding?.swipeRefresh?.isRefreshing = false
                    setUpAdapter()
                    //viewModel.refreshExchangeInfoList(REFRESH_DELAY_IN_MILLIS)
                }
                Status.ERROR -> {
                    requireView().snackbar(it.message ?: "An error occur while updating list")
                }
            }

        }

        mBinding?.swipeRefresh?.apply {
            setOnRefreshListener {
                viewModel.getExchangeInfoList()
            }
            isRefreshing = true
        }
    }

    private fun setUpAdapter() {
        val assetSortedList = ArrayList<CryptoDetailResponse>()
        for (cryptoDetail in cryptoRespArrayList) {
            if (cryptoDetail.quoteAsset.equals(quoteAsset.name, true)) {
                assetSortedList.add(cryptoDetail)
            }
        }

        val exchangeAdapter = assetSortedList.let {
            ExchangeAdapter(
                it,
                mainViewModel
            ) { position -> onListItemClick(it, position) }
        }
        mBinding?.exchangeRecycleView?.apply {
            adapter = exchangeAdapter
        }
    }

    private fun onListItemClick(arrayList: ArrayList<CryptoDetailResponse>, position: Int) {
        //val direction = HomeFragmentDirections.actionHomeFragmentToSymbolFragment(arrayList[position].symbol)
        val direction = HomeFragmentDirections.actionHomeFragmentToSymbolFragment()
        val bundle = bundleOf("symbol" to arrayList[position].symbol)
        val navigation = Navigation(direction, bundle)
        mainViewModel.applyNavigation(navigation)
    }

    companion object {
        const val REFRESH_DELAY_IN_MILLIS: Long = 5000
    }
}