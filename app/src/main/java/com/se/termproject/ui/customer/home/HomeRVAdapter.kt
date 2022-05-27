package com.se.termproject.ui.customer.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.se.termproject.data.Shop
import com.se.termproject.databinding.ItemShopsBinding

class HomeRVAdapter(private val mContext: Context) :
    RecyclerView.Adapter<HomeRVAdapter.ViewHolder>() {
    companion object {
        private const val TAG = "ADAPTER/SHOPS"
    }

    private val shops = ArrayList<Shop>()

    interface MyItemClickListner {
        fun onItemClick(shop: Shop, position: Int)
    }

    private lateinit var mItemClickListner: MyItemClickListner

    fun setMyItemClickListner(itemClickListner: MyItemClickListner) {
        mItemClickListner = itemClickListner
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemShopsBinding =
            ItemShopsBinding.inflate((LayoutInflater.from(viewGroup.context)))

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(shops[position])
        Glide.with(holder.itemView)
            .load(shops[position].coverImg)
            .into(holder.binding.itemShopsIv)
        Log.d(TAG, "shops[position].coverImg: ${shops[position].coverImg}")

        holder.itemView.setOnClickListener {
            mItemClickListner.onItemClick(
                shops[position],
                position
            )
        }
    }

    override fun getItemCount(): Int = shops.size

    inner class ViewHolder(val binding: ItemShopsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(shop: Shop) {
            binding.itemShopsNameTv.text = shop.name

//            binding.itemShopsIv.setImageResource(R.drawable.ic_logo)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(shops: ArrayList<Shop>) {
        this.shops.clear()
        this.shops.addAll(shops)
        notifyDataSetChanged()
    }
}
