package com.se.termproject.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

class ApplicationClass : Application() {
    companion object {
        const val TAG: String = "APP"
        const val USER_INFO: String = "USER_ID_INFO"
        lateinit var mSharedPreferences: SharedPreferences
        lateinit var USER_ID: String
    }

    @Override
    override fun onCreate() {
        super.onCreate()
        mSharedPreferences = applicationContext.getSharedPreferences(TAG, Context.MODE_PRIVATE)
    }
}
