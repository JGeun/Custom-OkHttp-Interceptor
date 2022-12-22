package com.jgeun.study.customokhttpinterceptor.network

import com.jgeun.study.customokhttpinterceptor.model.Item
import com.jgeun.study.customokhttpinterceptor.model.Person
import retrofit2.Call
import retrofit2.http.GET

interface NetworkService {

    /* body: {"code":"S200","message":"hello",
               "data":[{"name":"jgeun","age":26,"height":178.0},
                        {"name":"jgeun2","age":26,"height":178.0}]} */
    @GET("/api/item/test")
    fun getItemAPI(): Call<Item>

    /* body: {"pay":2000,"itemName":"ipad"} */
    @GET("/api/person/test")
    fun getPersonAPI(): Call<List<Person>>
}