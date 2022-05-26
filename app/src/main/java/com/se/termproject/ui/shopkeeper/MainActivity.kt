package com.se.termproject.ui.shopkeeper

import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.se.termproject.R
import com.se.termproject.base.kotlin.BaseActivity
import com.se.termproject.data.Shop
import com.se.termproject.databinding.ActivityShopkeeperBaseBinding
import com.se.termproject.util.getUserId
import com.se.termproject.util.saveUserId

class MainActivity :
    BaseActivity<ActivityShopkeeperBaseBinding>(ActivityShopkeeperBaseBinding::inflate),
    NavigationView.OnNavigationItemSelectedListener {
    companion object {
        private const val TAG = "ACT/MAIN"
        private lateinit var USER_ID: String
    }

    private lateinit var user: FirebaseUser
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mShopsReference: DatabaseReference
    private lateinit var mShopReference: DatabaseReference
    private lateinit var mChildEventListener: ChildEventListener

    override fun initAfterBinding() {
        mDatabase = FirebaseDatabase.getInstance()
        mShopsReference = mDatabase.getReference("shops")

        // 사용자 입력 받아오기
        user = Firebase.auth.currentUser!!
        saveUserId(user.uid)
        USER_ID = getUserId()!!

        initData()
        initDrawerLayout()
        initClickListener()
    }

    private fun initData() {
        mShopReference = mDatabase.getReference("shops").child(USER_ID)
        mShopsReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.getValue(Shop::class.java) == null) return

                val shop: Shop = dataSnapshot.getValue(Shop::class.java)!!
                Log.d(TAG, "shop: $shop")

                binding.shopkeeperMainLayout.shopkeeperNameTv.text = shop.name
                binding.shopkeeperMainLayout.shopkeeperTotalTableCountTv.text = shop.totalTableCount.toString()
                binding.shopkeeperMainLayout.shopkeeperAvailableTableCountTv.text = shop.availableTableCount.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) { }

        })
    }

    // drawer layout 초기화
    private fun initDrawerLayout() {
        binding.shopkeeperNav.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 등록하기
            R.id.menu_shopkeeper_nav_register_item -> {
                startNextActivity(RegisterActivity::class.java)
            }

            // 수정하기
            R.id.menu_shopkeeper_nav_edit_item -> {
                Toast.makeText(this, "수정하기", Toast.LENGTH_SHORT).show()
            }

            // 이메일 확인
            R.id.menu_shopkeeper_nav_email_item -> {
                Toast.makeText(this, "이메일 확인", Toast.LENGTH_SHORT).show()
            }

            // 이벤트 등록
            R.id.menu_shopkeeper_nav_event_item -> {
                Toast.makeText(this, "이벤트 등록", Toast.LENGTH_SHORT).show()
            }

            // 문의하기
            R.id.menu_shopkeeper_nav_ask_item -> {
                Toast.makeText(this, "문의하기", Toast.LENGTH_SHORT).show()
            }

            // 버전 정보
            R.id.menu_shopkeeper_nav_version_item -> {
                Toast.makeText(this, "버전 정보", Toast.LENGTH_SHORT).show()
            }
        }

        return false
    }

    // click listener 초기화
    private fun initClickListener() {
        binding.shopkeeperMainLayout.shopkeeperMenuSettingIv.setOnClickListener {
            if (!binding.shopkeeperBaseLayout.isDrawerOpen(GravityCompat.END)) {
                binding.shopkeeperBaseLayout.openDrawer(GravityCompat.END)
            }
        }

        val headerView = binding.shopkeeperNav.getHeaderView(0)
        headerView.findViewById<ImageView>(R.id.header_shopkeeper_menu_iv).setOnClickListener {
            binding.shopkeeperBaseLayout.closeDrawer(GravityCompat.END)
        }

        binding.shopkeeperMainLayout.shopkeeperOpenCloseBtn.setOnClickListener {
            Toast.makeText(this, "switch", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
