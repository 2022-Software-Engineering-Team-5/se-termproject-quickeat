package com.se.termproject.ui.customer.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.se.termproject.data.Shop
import com.se.termproject.databinding.ItemMarketBinding

class ShopRVAdapter(private val marketList: ArrayList<Shop>): RecyclerView.Adapter<ShopRVAdapter.ViewHolder>(){

    interface MyItemClickListner{
        fun onItemClick(shop : Shop)
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
        fun bind(shop : Shop){
            binding.itemNameTv.text = shop.name
//            binding.itemMarketIv.setImageResource(shop.coverImg!!)
        }
    }
}