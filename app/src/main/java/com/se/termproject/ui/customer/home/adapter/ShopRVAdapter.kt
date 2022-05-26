package com.se.termproject.ui.customer.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.se.termproject.R
import com.se.termproject.data.Shop
import com.se.termproject.databinding.ItemMarketBinding

class ShopRVAdapter : RecyclerView.Adapter<ShopRVAdapter.ViewHolder>() {
    private val shops = ArrayList<Shop>()

    interface MyItemClickListner {
        fun onItemClick(shop: Shop, position: Int)
    }

    private lateinit var mItemClickListner: MyItemClickListner

    fun setMyItemClickListner(itemClickListner: MyItemClickListner) {
        mItemClickListner = itemClickListner
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMarketBinding =
            ItemMarketBinding.inflate((LayoutInflater.from(viewGroup.context)))

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(shops[position])
        holder.itemView.setOnClickListener { mItemClickListner.onItemClick(shops[position], position) }
    }

    override fun getItemCount(): Int = shops.size

    inner class ViewHolder(val binding: ItemMarketBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(shop: Shop) {
            binding.itemNameTv.text = shop.name

            // FixMe: shop coverImg
            binding.itemMarketIv.setImageResource(R.drawable.ic_logo)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(shops: ArrayList<Shop>) {
        this.shops.clear()
        this.shops.addAll(shops)
        notifyDataSetChanged()
    }
}
