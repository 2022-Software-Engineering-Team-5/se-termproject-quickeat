package com.se.termproject.ui.shopkeeper

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.se.termproject.base.kotlin.BaseActivity
import com.se.termproject.data.Shop
import com.se.termproject.databinding.ActivityCheckBinding
import com.se.termproject.util.ApplicationClass.Companion.USER_ID
import com.se.termproject.util.getUserId
import com.se.termproject.util.saveUserId

class CheckActivity :
    BaseActivity<ActivityCheckBinding>(ActivityCheckBinding::inflate) {
    companion object {
        private const val TAG = "ACT/CHECK"
    }

    private lateinit var user: FirebaseUser
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mShopsReference: DatabaseReference

    override fun initAfterBinding() {
        // 사용자 입력 받아오기
        user = Firebase.auth.currentUser!!
        saveUserId(user.uid)
        USER_ID = getUserId()!!

        mDatabase = FirebaseDatabase.getInstance()
        mShopsReference = mDatabase.getReference("shops")

        mShopsReference.child("USER_ID").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshop: DataSnapshot) {
                if (dataSnapshop.getValue(Shop::class.java) == null) {
                    startNextActivity(RegisterActivity::class.java)
                } else {
                    startNextActivity(MainActivity::class.java)
                }
                finish()
            }

            override fun onCancelled(databaseError: DatabaseError) {}

        })
    }
}
