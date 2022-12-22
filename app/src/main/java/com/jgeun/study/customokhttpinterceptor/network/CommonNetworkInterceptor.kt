package com.jgeun.study.customokhttpinterceptor.network

import android.util.Log
import com.jgeun.study.customokhttpinterceptor.MyApplication
import com.jgeun.study.customokhttpinterceptor.MyApplication.Companion.ACCESS_TOKEN
import com.jgeun.study.customokhttpinterceptor.model.ResponseWrapper
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject

class CommonNetworkInterceptor : Interceptor {

    // Need to change your server's token key name
    private val headerTokenKey = "token"

    private val dataKey = "data"
    private val messageKey = "message"

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        /**
         * 1) Common Header with API Access Token
         *    API Access Token을 헤더에 추가하는 역할을 합니다
         */
        val accessToken = MyApplication.spUtil.getString(ACCESS_TOKEN, null)
        accessToken?.let {
            request = request.newBuilder()
                .addHeader(headerTokenKey, accessToken).build()
        }

        /**
         * 2) General Response from Server (Unwrapping data)
         */
        val response = chain.proceed(request)

        /**
         * 3) Parse body to json,
         * if body is in the form of [ResponseWrapper], save the message and result separately
         */
        val responseJson = response.extractResponseJson()
        val message = if (responseJson.has(messageKey)) responseJson[messageKey].toString() else ""
        val dataPayload = if (responseJson.has(dataKey)) responseJson[dataKey] else responseJson

        return response.newBuilder()
            .message(message)
            .body(dataPayload.toString().toResponseBody())
            .build()
    }

    private fun Response.extractResponseJson(): JSONObject {
        val jsonString: String = this.body?.string() ?: "{}"
        return try {
            JSONObject(jsonString)
        } catch(exception: Exception) {
            Log.d("UnboxingInterceptor", "서버 응답이 json이 아님: $jsonString")
            throw java.lang.Exception()
        }
    }
}