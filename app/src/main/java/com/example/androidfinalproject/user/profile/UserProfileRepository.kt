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
    var userData: MutableLiveData<UserProfile> = MutableLiveData<UserProfile>()

    fun updateUserProfile(id: String, userUpdate: UserUpdate) {
        userProfileAPI.updateUserProfile(id, userUpdate).enqueue(object : Callback<ResponseData> {
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
                    Gson().fromJson<UserProfile>(stringResponseData, UserProfile::class.java)
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
                val gson = Gson()
                val res = gson.toJson(response)
                val resData = gson.toJson(response?.result)
                userData.value = gson.fromJson<UserProfile>(resData, UserProfile::class.java)
                userResponse.value = gson.fromJson<ResponseData>(res, ResponseData::class.java)
            }
        })
    }
}