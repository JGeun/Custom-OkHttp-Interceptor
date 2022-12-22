package com.jgeun.study.customokhttpinterceptor.model

data class ResponseWrapper<T>(
    val code: String,
    val message: String,
    val data: T?
)
