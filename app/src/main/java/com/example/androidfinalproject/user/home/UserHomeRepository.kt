package com.example.androidfinalproject.user.home

import androidx.lifecycle.MutableLiveData
import com.example.androidfinalproject.utils.ResponseData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserHomeRepository @Inject constructor(val userHomeAPI: UserHomeAPI) {
    var userSaldoData: MutableLiveData<UserWallet> = MutableLiveData<UserWallet>()
    var userResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    fun getSaldoUser(id: String) {
        userHomeAPI.getSaldoUser(id).enqueue(object : Callback<ResponseData> {
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
                val userResponseObject =
                    Gson().fromJson<ResponseData>(stringResponse, ResponseData::class.java)
                val userResponseDataObject =
                    Gson().fromJson<UserWallet>(stringResponseData, UserWallet::class.java)
                userResponse.value = userResponseObject
                userSaldoData.value = userResponseDataObject
            }
        })
    }
}