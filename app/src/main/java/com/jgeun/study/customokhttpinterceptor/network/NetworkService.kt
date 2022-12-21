package com.jgeun.study.customokhttpinterceptor.network

import com.jgeun.study.customokhttpinterceptor.model.Item
import com.jgeun.study.customokhttpinterceptor.model.Person
import retrofit2.Call
import retrofit2.http.GET

interface NetworkService {

    @GET("/api/item/test")
    fun getItemAPI(): Call<Item>

    @GET("/api/person/test")
    fun getPersonAPI(): Call<Person>
}