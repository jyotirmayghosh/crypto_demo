package com.jyotirmayg.cryptodemo.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.jyotirmayg.cryptodemo.base.BaseViewHolder
import com.jyotirmayg.cryptodemo.data.apiModel.CryptoDetailResponse
import com.jyotirmayg.cryptodemo.databinding.ItemCoinBinding
import com.jyotirmayg.cryptodemo.feature.MainViewModel
import java.util.ArrayList

class ExchangeAdapter(
    private val exchangeList: ArrayList<CryptoDetailResponse>,
    private val viewModel: MainViewModel,
    private val onItemClicked: (position: Int) -> Unit
) :
    RecyclerView.Adapter<BaseViewHolder<CryptoDetailResponse>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CryptoDetailResponse> {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCoinBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<CryptoDetailResponse>, position: Int) {
        holder.bindData(exchangeList[position])
    }

    override fun getItemCount(): Int {
        return exchangeList.size
    }

    inner class ViewHolder(private val binding: ViewDataBinding) :
        BaseViewHolder<CryptoDetailResponse>(binding), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun bindData(dataModel: CryptoDetailResponse) {
            with(binding) {
                setVariable(BR.viewmodel, viewModel)
                setVariable(BR.coin, dataModel)
                executePendingBindings()
            }
        }

        override fun onClick(p0: View?) {
            onItemClicked(adapterPosition)
        }
    }
}