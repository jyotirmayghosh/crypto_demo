package com.jyotirmayg.cryptodemo.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding?> : AppCompatActivity() {
    protected var mBinding: T? = null
    private var mActivity: AppCompatActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
        mBinding = DataBindingUtil.setContentView<T>(this, findContentView())
        mBinding?.executePendingBindings()
        onReady(savedInstanceState)
    }

    @LayoutRes
    abstract fun findContentView(): Int
    protected abstract fun onReady(savedInstanceState: Bundle?)
}