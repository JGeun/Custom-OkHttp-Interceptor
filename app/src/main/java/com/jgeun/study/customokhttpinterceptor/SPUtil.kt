package com.jgeun.study.customokhttpinterceptor

import android.content.Context
import android.content.SharedPreferences

class SPUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("data_file", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String?): String? {
        return prefs.getString(key, defValue)
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }
}