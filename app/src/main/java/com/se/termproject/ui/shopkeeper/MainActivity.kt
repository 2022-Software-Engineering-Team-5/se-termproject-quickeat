package com.se.termproject.ui.shopkeeper

import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.se.termproject.R
import com.se.termproject.base.kotlin.BaseActivity
import com.se.termproject.databinding.ActivityShopkeeperBaseBinding
import com.se.termproject.ui.intro.IntroActivity

class MainActivity :
    BaseActivity<ActivityShopkeeperBaseBinding>(ActivityShopkeeperBaseBinding::inflate),
    NavigationView.OnNavigationItemSelectedListener {

    override fun initAfterBinding() {
        initDrawerLayout()
        initClickListener()
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