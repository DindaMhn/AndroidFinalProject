package com.example.androidfinalproject.provider.profile

import androidx.lifecycle.MutableLiveData
import com.example.androidfinalproject.user.profile.UserProfile
import com.example.androidfinalproject.utils.ResponseData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProviderProfileRepository @Inject constructor(val providerProfileAPI: ProviderProfileAPI) {
    var providerResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    var providerData: MutableLiveData<ProviderProfile> = MutableLiveData<ProviderProfile>()

    fun updateProviderProfile(id: String, providerUpdate: ProviderUpdate) {
        providerProfileAPI.updateProviderProfile(id, providerUpdate)
            .enqueue(object : Callback<ResponseData> {
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    println("RESPONSE "+response.body())
                    val response = response.body()
                    val gson = Gson()
                    val res = gson.toJson(response)
                    val resData = gson.toJson(response?.result)
                    println(resData)
                    providerData.value = gson.fromJson<ProviderProfile>(resData, ProviderProfile::class.java)
                    providerResponse.value = gson.fromJson<ResponseData>(res, ResponseData::class.java)
                }
            })
    }

    fun deleteProviderPhoto(id: String) {
        providerProfileAPI.deleteProviderPhoto(id).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
                providerResponse.value = gson.fromJson<ResponseData>(res, ResponseData::class.java)
            }
        })
    }
    fun getProvider(id: String) {
        providerProfileAPI.getProvider(id).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val resData = gson.toJson(response?.result)
                providerData.value = gson.fromJson<ProviderProfile>(resData, ProviderProfile::class.java)
            }

        })
    }
}