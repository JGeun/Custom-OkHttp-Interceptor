package com.jgeun.study.customokhttpinterceptor.model

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("itemName")
    val itemName: String,
    val pay: Int
)
