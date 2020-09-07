package com.example.androidfinalproject.user.search

import androidx.lifecycle.MutableLiveData
import com.example.androidfinalproject.user.account.User
import com.example.androidfinalproject.utils.ResponseData
import com.example.androidfinalproject.utils.ResponseLocation
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class LocationRepository @Inject constructor(val locationAPI : LocationAPI) {
//    var location : MutableLiveData<List<Location>> = MutableLiveData<List<Location>>()
//    var listLocation : MutableList<MutableLiveData<Location>> = MutableList<MutableLiveData<Location>>()
    var listLocation: MutableLiveData<List<Location>> = MutableLiveData<List<Location>>()
    fun getLocation(){
        locationAPI.getAsset().enqueue(object : Callback<ResponseLocation>{
            override fun onFailure(call: Call<ResponseLocation>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseLocation>, response: Response<ResponseLocation>) {
                val response = response.body()
                val gson = Gson()
                val resData = gson.toJson(response?.response)
                listLocation.value = gson.fromJson(gson.toJson(resData),Array<Location>::class.java).toList()
            }
        })
    }
}