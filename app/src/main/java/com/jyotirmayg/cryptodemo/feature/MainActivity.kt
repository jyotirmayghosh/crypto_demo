package com.jyotirmayg.cryptodemo.feature

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.jyotirmayg.cryptodemo.R
import com.jyotirmayg.cryptodemo.base.BaseActivity
import com.jyotirmayg.cryptodemo.databinding.ActivityMainBinding
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : BaseActivity<ActivityMainBinding>(), KodeinAware {

    override val kodein: Kodein by kodein()
    private val factory: MainViewModelFactory by instance()
    private lateinit var viewModel: MainViewModel

    private fun navController() = findNavController(R.id.nav_host_fragment)

    override fun findContentView(): Int = R.layout.activity_main

    override fun onReady(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this@MainActivity, factory)[MainViewModel::class.java]

        viewModel.navigation.observe(this) {
            val nav =  it
            if (nav == null){
                navigationUp()
            } else {
                mBinding?.let { navController().navigate(nav.direction!!.actionId, nav.bundle) }
            }
        }
    }

    private fun navigationUp() {
        navController().popBackStack()
    }
}
