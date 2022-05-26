package com.se.termproject.ui.intro

import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import com.se.termproject.base.kotlin.BaseActivity
import com.se.termproject.databinding.ActivityIntroBinding
import com.se.termproject.ui.login.LoginActivity
import com.se.termproject.ui.shopkeeper.CheckActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class IntroActivity : BaseActivity<ActivityIntroBinding>(ActivityIntroBinding::inflate) {
    private lateinit var mSharedPreferences: SharedPreferences

    override fun initAfterBinding() {
        // sharedPrefrences 초기화
        mSharedPreferences = getSharedPreferences("mode", MODE_PRIVATE)

        // click listener 초기화
        initClickListener()
    }

    private fun initClickListener() {

        // customer mode (0)
        binding.introCustomerIv.setOnClickListener {
            val editor = mSharedPreferences.edit()
            editor.putInt("mode", 0)
            editor.apply()

            startNextActivity(LoginActivity::class.java)
            finish()
        }

        // shopkeeper mode (1)
        binding.introShopkeeperIv.setOnClickListener {
            val editor = mSharedPreferences.edit()
            editor.putInt("mode", 1)
            editor.apply()

            startNextActivity(LoginActivity::class.java)
            finish()
        }
    }
}
