package com.se.termproject.ui.customer.history

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.se.termproject.base.kotlin.BaseFragment
import com.se.termproject.data.Customer
import com.se.termproject.data.Review
import com.se.termproject.databinding.FragmentHistoryBinding
import com.se.termproject.util.ApplicationClass
import com.se.termproject.util.ApplicationClass.Companion.USER_ID
import com.se.termproject.util.getUserId
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate) {
    companion object {
        private const val TAG = "FRAG/HISTORY"
    }

    private var reviews = ArrayList<Review>()
    private var user: FirebaseUser? = null

    // binding error 막기 위해
    private var jobs: ArrayList<Job> = arrayListOf()

    private lateinit var histroyRVAdapter: HistoryRVAdapter

    // firebase
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mCustomerReference: DatabaseReference

    override fun initAfterBinding() {
        user = FirebaseAuth.getInstance().currentUser

        initStore()
        initRecyclerView()
        binding()
    }

    // store data 초기화 - firebase 연동 후 구현
    private fun initStore() {
        reviews.clear()
        USER_ID = getUserId()!!
        mDatabase = FirebaseDatabase.getInstance()
        mCustomerReference = mDatabase.getReference("customers")

        mCustomerReference.child(USER_ID).child("Review")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var reviewCount: Long = 0L

                    if (view != null) {
                        jobs.add(viewLifecycleOwner.lifecycleScope.launch {
                            reviewCount = dataSnapshot.childrenCount
                            binding.historyProfileNumberOfStoreTv.text = reviewCount.toString()
                        })
                    }

                    Log.d(TAG, reviewCount.toString())
                    Log.d(TAG, dataSnapshot.toString())
                    for (data in dataSnapshot.children) {
                        val tempDate: String = data.key.toString()
                        val tempValueString: String = data.value.toString()
                        val realReview: String = tempValueString.substring(
                            tempValueString.indexOf("user_review=") + 12,
                            tempValueString.indexOf(',')
                        )
                        val shopName: String = tempValueString.substring(
                            tempValueString.indexOf("shop_name=") + 10,
                            tempValueString.lastIndexOf('}')
                        )

                        val addedReview: Review = Review(shopName, tempDate, realReview)
                        Log.d(TAG, "addedReview: $addedReview")

                        reviews.add(addedReview)
                    }

                    // RecyclerView에 데이터셋 전달
                    histroyRVAdapter.addData(reviews)

                    // reviews.add()
                    // HistoryRVAdapter.addData(shops)
                }

                override fun onCancelled(databaseError: DatabaseError) {}

            })
    }

    // RecyclerView 초기화
    private fun initRecyclerView() {
        histroyRVAdapter = HistoryRVAdapter()
        binding.rankingRv.adapter = histroyRVAdapter
    }

    private fun binding() {
        binding.historyProfileNameTv.text = user!!.displayName
        binding.historyProfileEmailTv.text = user!!.email
    }

    override fun onDestroyView() {
        // 등록된 jobs 취소
        // 취소하지 않으면 binding error가 발생한다. (Null point exception)
        for (job in jobs) {
            job.cancel()
        }

        super.onDestroyView()
    }
}
