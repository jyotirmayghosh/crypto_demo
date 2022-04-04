package com.jyotirmayg.cryptodemo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


abstract class BaseFragment<T : ViewDataBinding?> : Fragment() {

    protected var mBinding: T? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return if (view == null) {
            mBinding = DataBindingUtil.inflate<T>(inflater, findContentView(), container, false)
            mBinding?.root
        } else
            view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (getView() != null)
            onReady()
    }


    @LayoutRes
    abstract fun findContentView(): Int
    protected abstract fun onReady()
}