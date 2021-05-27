package com.jvmartinez.marvelinfo.core.data.local

import android.content.SharedPreferences

class Preferences(private val sharedPreferences: SharedPreferences) {
    private val edit: SharedPreferences.Editor = sharedPreferences.edit()
    private val KEY_ISlOGIN: String = "isLogin"
    private val KEY_DISPLAY_NAME: String = "displayName"

    fun getIsLogin() = sharedPreferences.getBoolean(KEY_ISlOGIN, false)

    fun setIsLogin(isLogin: Boolean) {
        edit.putBoolean(KEY_ISlOGIN, isLogin)
        edit.apply()
    }

    fun getDisplayName() = sharedPreferences.getString(KEY_DISPLAY_NAME, "").toString()

    fun setDisplayName(display: String) {
        edit.putString(KEY_DISPLAY_NAME, display)
        edit.apply()
    }
}