package com.se.termproject.ui.customer.history

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.se.termproject.base.kotlin.BaseFragment
import com.se.termproject.data.Review
import com.se.termproject.data.Shop
import com.se.termproject.databinding.FragmentHistoryBinding
import com.se.termproject.ui.customer.history.HistoryRVAdapter
import com.se.termproject.ui.customer.history.Store
import com.se.termproject.ui.shopkeeper.MainActivity
import com.se.termproject.ui.shopkeeper.RegisterActivity
import com.se.termproject.util.ApplicationClass
import com.se.termproject.util.ApplicationClass.Companion.USER_ID
import com.se.termproject.util.getUserId

class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate) {
    private var storeData = ArrayList<Store>()
    private var reviews = ArrayList<Review>()

    private lateinit var histroyRVAdapter: HistoryRVAdapter

    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mCustomerReference: DatabaseReference

    override fun initAfterBinding() {
        initStore()
        initRecyclerView()
        binding()
    }

    //store data 초기화 - firebase 연동 후 구현
    private fun initStore(){

        ApplicationClass.USER_ID = getUserId()!!
        mDatabase = FirebaseDatabase.getInstance()
        mCustomerReference = mDatabase.getReference("customers")

        mCustomerReference.child(USER_ID).child("Review").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Log.d("여기-리뷰", dataSnapshot.value.toString())
                for (data in dataSnapshot.children) {
                    var temp_date :String = data.key.toString()
                    var temp_value_string :String = data.value.toString()
                    var real_review :String = temp_value_string.substring(temp_value_string.indexOf("user_review=") + 12, temp_value_string.indexOf(','))
                    var shop_name :String = temp_value_string.substring(temp_value_string.indexOf("shop_name=") + 10, temp_value_string.lastIndexOf('}'))

                    val added_review : Review = Review(shop_name, temp_date, real_review)
                    reviews.add(added_review)
                }
                //reviews.add()

                //HistoryRVAdapter.addData(shops)

            }

            override fun onCancelled(databaseError: DatabaseError) {}

        })

    }

    //RecyclerView 초기화
    private fun initRecyclerView(){
        histroyRVAdapter = HistoryRVAdapter(storeData)
        binding.rankingRv.adapter = histroyRVAdapter
    }

    private fun binding(){

    }
}