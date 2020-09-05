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
                val stringResponse = Gson().toJson(response)
                val stringResponseData = Gson().toJson(response?.result)
                val userLoginResponseObject =
                    Gson().fromJson<ResponseData>(stringResponse, ResponseData::class.java)
                val userLoginResponseDataObject =
                    Gson().fromJson<User>(stringResponseData, User::class.java)
                userResponse.value = userLoginResponseObject
                userData.value = userLoginResponseDataObject

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
                val stringResponse = Gson().toJson(response)
                val userRegisterResponseObject =
                    Gson().fromJson<ResponseData>(stringResponse, ResponseData::class.java)
                userResponse.value = userRegisterResponseObject
            }
        })
    }

}