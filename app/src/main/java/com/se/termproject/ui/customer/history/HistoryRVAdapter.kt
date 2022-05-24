package com.se.termproject.ui.main.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.se.termproject.databinding.ItemHistoryStoreBinding
import com.se.termproject.ui.customer.history.Store


class HistoryRVAdapter(private var marketList : ArrayList<Store>) : RecyclerView.Adapter<HistoryRVAdapter.ViewHolder>() {

    // ViewHolder
    inner class ViewHolder(val binding: ItemHistoryStoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(store : Store){
            binding.historyStoreIv.setImageResource(store.image!!)
            binding.historyStoreTv.text = store.market_name
        }
    }

    // ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryRVAdapter.ViewHolder {
        val binding: ItemHistoryStoreBinding = ItemHistoryStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // ViewHolder binding
    override fun onBindViewHolder(holder: HistoryRVAdapter.ViewHolder, position: Int) {
        holder.bind(marketList[position])
    }

    // 데이터셋의 크기를 알려준다.
    override fun getItemCount(): Int = marketList.size

    // 데이터셋 추가
    @SuppressLint("NotifyDataSetChanged")
    fun addData(marketList: ArrayList<Store>) {
        this.marketList.clear()
        this.marketList.addAll(marketList)
        notifyDataSetChanged()
    }
}