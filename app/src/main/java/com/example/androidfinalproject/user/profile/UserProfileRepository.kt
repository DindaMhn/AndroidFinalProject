package com.example.androidfinalproject.user.profile

import androidx.lifecycle.MutableLiveData
import com.example.androidfinalproject.user.account.User
import com.example.androidfinalproject.utils.ResponseData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserProfileRepository @Inject constructor(val userProfileAPI: UserProfileAPI) {
    var userResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    var userData: MutableLiveData<User> = MutableLiveData<User>()

    fun updateUserProfile(id: String, user: User) {
        userProfileAPI.updateUserProfile(id, user).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val stringResponseData = Gson().toJson(response?.result)
                val userResponseObject =
                    Gson().fromJson<ResponseData>(stringResponse, ResponseData::class.java)
                val userResponseDataObject =
                    Gson().fromJson<User>(stringResponseData, User::class.java)
                userResponse.value = userResponseObject
                userData.value = userResponseDataObject
            }
        })
    }

    fun deleteUserPhoto(id: String) {
        userProfileAPI.deleteUserPhoto(id).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val stringResponseData = Gson().toJson(response?.result)
                val userResponseObject =
                    Gson().fromJson<ResponseData>(stringResponse, ResponseData::class.java)
                val userResponseDataObject =
                    Gson().fromJson<User>(stringResponseData, User::class.java)
                userResponse.value = userResponseObject
                userData.value = userResponseDataObject
            }
        })
    }
}