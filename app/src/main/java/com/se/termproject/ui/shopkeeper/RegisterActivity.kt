package com.se.termproject.ui.shopkeeper

import com.se.termproject.base.kotlin.BaseActivity
import com.se.termproject.databinding.ActivityShopkeeperRegisterBinding

class RegisterActivity : BaseActivity<ActivityShopkeeperRegisterBinding>(ActivityShopkeeperRegisterBinding::inflate) {

    override fun initAfterBinding() {
        initKakaoMapApi()
        initClickListener()
    }

    private fun initKakaoMapApi() {

    }

    private fun initClickListener() {

    }
}