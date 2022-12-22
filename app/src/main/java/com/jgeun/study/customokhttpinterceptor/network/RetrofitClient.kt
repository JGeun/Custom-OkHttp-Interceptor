package com.jgeun.study.customokhttpinterceptor.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val baseUrl = "http://10.0.2.2:3001"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(5000, TimeUnit.SECONDS)
        .connectTimeout(5000, TimeUnit.MILLISECONDS)
        .addInterceptor(HttpLoggingInterceptor { message: String ->
            Log.d("network_info", message)
        }.setLevel(HttpLoggingInterceptor.Level.BODY))
        .addNetworkInterceptor(CommonNetworkInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitService = retrofit.create(NetworkService::class.java)

    fun getNetworkService(): NetworkService = retrofitService
}