package com.se.termproject.ui.customer.setting

import android.content.Intent
import android.widget.Toast
import com.se.termproject.base.kotlin.BaseFragment
import com.se.termproject.databinding.FragmentSettingBinding
import com.se.termproject.ui.login.LoginActivity
import com.se.termproject.util.ApplicationClass
import com.se.termproject.util.ApplicationClass.Companion.USER_ID
import com.se.termproject.util.getUserId
import com.se.termproject.util.saveUserId

class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    override fun initAfterBinding() {
        initClickListener()
    }

    // initialize click listener
    private fun initClickListener() {

        binding.settingMenuLogoutLl.setOnClickListener {
            // 로그아웃
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        // when you click '내 정보 수정'
        binding.settingMenuEditLl.setOnClickListener {
            // 정보 수정할 수 있는 화면으로 넘어가야 한다.
        }

        // when you click '리뷰 남기기'
        binding.settingMenuReviewLl.setOnClickListener {
            Toast.makeText(context, "앱 출시 후 추가될 기능입니다.", Toast.LENGTH_SHORT).show()
        }

        // when you click '문의하기'
        binding.settingMenuAskLl.setOnClickListener {
            Toast.makeText(context, "이메일: gachon.se.team5@gmail.com", Toast.LENGTH_SHORT).show()
        }

        // when you click '버전정보: 1.00'
        binding.settingMenuVersionLl.setOnClickListener {
            Toast.makeText(context, "최신 버전입니다.", Toast.LENGTH_SHORT).show()
        }
    }
}