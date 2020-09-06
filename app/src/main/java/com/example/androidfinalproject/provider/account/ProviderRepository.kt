package com.example.androidfinalproject.provider.account

import androidx.lifecycle.MutableLiveData
import com.example.androidfinalproject.utils.ResponseData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProviderRepository @Inject constructor(val providerAPI: ProviderAPI) {
    var providerData: MutableLiveData<Provider> = MutableLiveData<Provider>()
    var providerResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()

    fun loginProvider(provider: Provider) {
        providerAPI.loginProvider(provider).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                println(t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {

                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val stringResponseData = Gson().toJson(response?.result)
                val providerLoginResponseObject =
                    Gson().fromJson<ResponseData>(stringResponse, ResponseData::class.java)
                val providerLoginResponseDataObject =
                    Gson().fromJson<Provider>(
                        stringResponseData,
                        Provider::class.java
                    )
                providerResponse.value = providerLoginResponseObject
                providerData.value = providerLoginResponseDataObject
            }
        })
    }

    fun registerProvider(provider: Provider) {
        providerAPI.registerProvider(provider).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val providerRegisterResponseObject =
                    Gson().fromJson<ResponseData>(stringResponse, ResponseData::class.java)
                providerResponse.value = providerRegisterResponseObject
            }
        })
    }
}