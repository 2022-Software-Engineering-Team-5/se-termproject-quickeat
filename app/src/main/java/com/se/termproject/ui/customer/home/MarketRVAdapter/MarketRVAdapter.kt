package com.se.termproject.ui.customer.home.MarketRVAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.se.termproject.databinding.ItemMarketBinding

class MarketRVAdapter(private val marketList: ArrayList<Market>): RecyclerView.Adapter<MarketRVAdapter.ViewHolder>(){

    interface MyItemClickListner{
        fun onItemClick(market : Market)
    }
    private lateinit var mItemClickListner: MyItemClickListner

    fun setMyItemClickListner(itemClickListner: MyItemClickListner){
        mItemClickListner = itemClickListner
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMarketBinding = ItemMarketBinding.inflate((LayoutInflater.from(viewGroup.context)))

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(marketList[position])
        holder.itemView.setOnClickListener { mItemClickListner.onItemClick(marketList[position]) }
    }

    override fun getItemCount(): Int = marketList.size

    inner class ViewHolder(val binding: ItemMarketBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(market : Market){
            binding.itemNameTv.text = market.name
            binding.itemMarketIv.setImageResource(market.coverImg!!)
        }
    }
}