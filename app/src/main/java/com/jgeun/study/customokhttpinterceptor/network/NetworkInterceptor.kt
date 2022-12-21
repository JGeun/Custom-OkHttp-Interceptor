package com.jgeun.study.customokhttpinterceptor.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.jgeun.study.customokhttpinterceptor.model.*
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.stream.IntStream.range

class CommonNetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        /**
         * 1) Common Header with API Access Token
         */
        /*val newRequest = chain.request().newBuilder()
            .addHeader("token", SPUtil.accessToken).build()*/

        /**
         * 2) General Response from Server (Unwrapping data)
         */
        val response = chain.proceed(chain.request())

        /**
         * 3) Parse body to json
         */
        val rawJson = response.body?.string() ?: "{}"

        /**
         * 4) Wrap body with gson
         */
        val gson = Gson()
        val type = object : TypeToken<ResponseWrapper<*>>() {}.type
        val res = try {
            val r = gson.fromJson<ResponseWrapper<*>>(rawJson, type)
                ?: throw JsonSyntaxException("Parse Fail")

            /*if (!r.)
                ResponseWrapper<Any>(-999, false, "Server Logic Fail : ${r.message}", null)
            else*/
            r

        } catch (e: JsonSyntaxException) {
            ResponseWrapper<Any>("-999", "json parsing fail : $e", null)
        } catch (t: Throwable) {
            ResponseWrapper<Any>("-999", "unknown error : $t", null)
        }

        /**
         * 5) get data json from data
         */
        val dataJson = gson.toJson(res.result)

        /**
         * 6) return unwrapped response with body
         */
        return response.newBuilder()
            .message(res.message)
            .body(dataJson.toString().toResponseBody())
            .build()
    }

    /*private fun translateBaseData(body: String) {
        val runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(ViewObject::class.java, "type")
            .registerSubtype(TestItem::class.java, "item")

        val gson = GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create()

//        val gson = Gson()
        val type = object : TypeToken<CommonItem>() {}.type
        val baseData: Any = gson.fromJson<CommonItem>(body, type)
        Log.d("Network", "translate: ${baseData}")
        return gson.fromJson(body, type)
    }*/

    private fun extractResponseJson(body: String): JSONObject {
        val jsonString: String = body
        return try {
            JSONObject(jsonString)
        } catch (exception: Exception) {
            Log.d("UnboxingInterceptor", "서버 응답이 json이 아님: $jsonString")
            throw java.lang.Exception()
        }
    }
}