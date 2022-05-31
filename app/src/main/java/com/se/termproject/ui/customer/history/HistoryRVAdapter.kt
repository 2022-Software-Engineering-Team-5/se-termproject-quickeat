package com.se.termproject.ui.customer.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.se.termproject.data.Review
import com.se.termproject.databinding.ItemHistoryStoreBinding

class HistoryRVAdapter() :
    RecyclerView.Adapter<HistoryRVAdapter.ViewHolder>() {
    companion object {
        private const val TAG = "ADAPTER/REVIEW"
    }

    private var reviews = ArrayList<Review>()

    // ViewHolder
    inner class ViewHolder(val binding: ItemHistoryStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.historyStoreTitle.text = review.shopName
            binding.historyStoreDate.text = review.date
            binding.historyStoreReview.text = review.review
        }
    }

    // ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemHistoryStoreBinding =
            ItemHistoryStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // ViewHolder binding
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    // 데이터셋의 크기를 알려준다.
    override fun getItemCount(): Int = reviews.size

    // 데이터셋 추가
    @SuppressLint("NotifyDataSetChanged")
    fun addData(reviews: ArrayList<Review>) {
        this.reviews.clear()
        this.reviews.addAll(reviews)
        notifyDataSetChanged()
    }
}
