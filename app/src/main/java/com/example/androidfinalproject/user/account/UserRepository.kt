package com.example.androidfinalproject.user.account

import androidx.lifecycle.MutableLiveData
import com.example.androidfinalproject.utils.ResponseData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(val userAPI: UserAPI) {
    var userData: MutableLiveData<User> = MutableLiveData<User>()
    var userResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()

    fun loginUser(user: User) {
        userAPI.loginUser(user).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                println(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
                val resData = gson.toJson(response?.result)
                userData.value = gson.fromJson<User>(resData, User::class.java)
                userResponse.value = gson.fromJson<ResponseData>(res, ResponseData::class.java)
            }
        })
    }

    fun registerUser(user: User) {
        userAPI.registerUser(user).enqueue(object : Callback<ResponseData> {
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
                userData.value = gson.fromJson<User>(resData, User::class.java)
                userResponse.value = gson.fromJson<ResponseData>(res, ResponseData::class.java)
            }
        })
    }

}