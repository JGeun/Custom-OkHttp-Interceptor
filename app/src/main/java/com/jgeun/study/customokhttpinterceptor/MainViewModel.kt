package com.jgeun.study.customokhttpinterceptor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jgeun.study.customokhttpinterceptor.model.Item
import com.jgeun.study.customokhttpinterceptor.model.Person
import com.jgeun.study.customokhttpinterceptor.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _person = MutableLiveData<Person>()
    val person: LiveData<Person> get() = _person

    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item> get() = _item

    init {
        callItemAPI()

        callPersonAPI()
    }

    private fun callItemAPI() {
        RetrofitClient.getNetworkService().getItemAPI().enqueue(object: Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful) {
                    Log.d("Item API Success", response.message())
                    _item.value = response.body()
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.d("Item API Failure", "${t.message}")
            }
        })
    }


    private fun callPersonAPI() {
        RetrofitClient.getNetworkService().getPersonAPI().enqueue(object: Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (response.isSuccessful) {
                    _person.value = response.body()
                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                Log.d("Person API Failure", "${t.message}")
            }
        })
    }
}