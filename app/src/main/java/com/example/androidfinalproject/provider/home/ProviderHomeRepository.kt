package com.example.androidfinalproject.provider.home

import androidx.lifecycle.MutableLiveData
import com.example.androidfinalproject.provider.account.Provider
import com.example.androidfinalproject.provider.account.ProviderAPI
import com.example.androidfinalproject.utils.ResponseData
import com.google.gson.Gson
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProviderHomeRepository @Inject constructor(val providerHomeAPI: ProviderHomeAPI) {
    var providerData: MutableLiveData<Provider> = MutableLiveData<Provider>()
    var providerResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    var providerAssetData: MutableLiveData<Asset> = MutableLiveData<Asset>()
    fun getSaldoProvider(id: String) {
        providerHomeAPI.getSaldoProvider(id).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val stringResponseData = Gson().toJson(response?.result)
                val providerResponseObject =
                    Gson().fromJson<ResponseData>(stringResponse, ResponseData::class.java)
                providerResponse.value = providerResponseObject
            }
        })
    }

    fun createAsset(photo: MultipartBody.Part, result: MultipartBody.Part) {
        providerHomeAPI.createAsset(photo, result)
            .enqueue(object : Callback<ResponseData> {
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    val response = response.body()
                    val gson = Gson()
                    val res = gson.toJson(response)
                    providerResponse.value =
                        gson.fromJson<ResponseData>(res, ResponseData::class.java)
                }
            })
    }
}