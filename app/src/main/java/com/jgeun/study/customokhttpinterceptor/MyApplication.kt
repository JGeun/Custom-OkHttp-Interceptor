package com.jgeun.study.customokhttpinterceptor

import android.app.Application

class MyApplication : Application() {

    companion object {
        lateinit var spUtil: SPUtil
        const val ACCESS_TOKEN = "ACCESS-TOKEN"
    }

    override fun onCreate() {
        super.onCreate()
        spUtil = SPUtil(applicationContext)
    }
}