package com.example.androidfinalproject.user.home

import androidx.lifecycle.MutableLiveData
import com.example.androidfinalproject.user.account.User
import com.example.androidfinalproject.utils.ResponseData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserHomeRepository @Inject constructor(val userHomeAPI: UserHomeAPI) {
    var userSaldoData: MutableLiveData<UserWallet> = MutableLiveData<UserWallet>()
    var userResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    var userTicketData:MutableLiveData<UserTicket> = MutableLiveData<UserTicket>()

    fun getSaldoUser(id: String,token: String) {
        userHomeAPI.getSaldoUser(id,token).enqueue(object : Callback<ResponseData> {
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
                val resData = gson.toJson(response?.result)
                userSaldoData.value = gson.fromJson<UserWallet>(resData, UserWallet::class.java)
                userResponse.value = gson.fromJson<ResponseData>(res, ResponseData::class.java)
            }
        })
    }
    fun updateSaldoUser(id:String,token: String, userWallet:UserWallet){
        userHomeAPI.updateSaldoUser(id,token,userWallet).enqueue(object:Callback<ResponseData>{
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
                val resData = gson.toJson(response?.result)
                userResponse.value = gson.fromJson<ResponseData>(res, ResponseData::class.java)
            }
        })
    }
    fun getUserTicket(id:String,token: String){
        userHomeAPI.getUserTicket(id,token).enqueue(object:Callback<ResponseData>{
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
                val resData = gson.toJson(response?.result)
                userResponse.value = gson.fromJson<ResponseData>(res, ResponseData::class.java)
                userTicketData.value = gson.fromJson<UserTicket>(resData,UserTicket::class.java)
            }
        })
    }
}