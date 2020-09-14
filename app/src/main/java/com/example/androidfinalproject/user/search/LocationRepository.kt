package com.example.androidfinalproject.user.search

import androidx.lifecycle.MutableLiveData
import com.example.androidfinalproject.utils.ResponseLocation
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class LocationRepository @Inject constructor(val locationAPI : LocationAPI) {
    var listLocation: MutableLiveData<List<Location>> = MutableLiveData<List<Location>>()
    var detailLocation:MutableLiveData<Location> = MutableLiveData<Location>()
    fun getLocation(token:String){
        locationAPI.getAsset(token).enqueue(object : Callback<ResponseLocation>{
            override fun onFailure(call: Call<ResponseLocation>, t: Throwable) {
                t.printStackTrace()
                println("gak masuk")
            }

            override fun onResponse(call: Call<ResponseLocation>, response: Response<ResponseLocation>) {
                val response = response.body()
                if(response?.message == "Success"){
                    println("masuk 2")
                    val res = response.response
                    val gson = Gson()
                    listLocation.value = gson.fromJson(gson.toJson(res),Array<Location>::class.java).toList()
                }else{
                    println("Data Not Found")
                }
            }
        })
    }
    fun getDateilLocation(id:String,token: String){
        locationAPI.getAssetByID(id,token).enqueue(object : Callback<ResponseLocation>{
            override fun onFailure(call: Call<ResponseLocation>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ResponseLocation>,
                response: Response<ResponseLocation>
            ) {
                val response = response.body()
                if(response?.message == "Success"){
                    val res = response.response
                    val gson = Gson()
                    detailLocation.value = gson.fromJson(gson.toJson(res),Location::class.java)
                }else{
                    println("Data Not Found")
                }
            }

        })
    }

}