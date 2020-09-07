package com.example.androidfinalproject.user.profile

import androidx.lifecycle.MutableLiveData
import com.example.androidfinalproject.utils.ResponseData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserProfileRepository @Inject constructor(val userProfileAPI: UserProfileAPI) {
    var userResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    var userData: MutableLiveData<UserProfile> = MutableLiveData<UserProfile>()
    var userResponsePhoto :  MutableLiveData<ResponsePhoto> = MutableLiveData<ResponsePhoto>()

    fun updateUserProfile(id: String, userUpdate: UserUpdate) {
        userProfileAPI.updateUserProfile(id, userUpdate).enqueue(object : Callback<ResponseData> {
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

    fun getUser(id: String) {
        userProfileAPI.getUser(id).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val resData = gson.toJson(response?.result)
                userData.value = gson.fromJson<UserProfile>(resData, UserProfile::class.java)
            }

        })
    }
    fun getUserPhoto(id: String) {
        userProfileAPI.getUserPhoto(id).enqueue(object : Callback<ResponsePhoto> {
            override fun onFailure(call: Call<ResponsePhoto>, t: Throwable) {
                t.printStackTrace()
            }
            override fun onResponse(call: Call<ResponsePhoto>, response: Response<ResponsePhoto>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
                userResponsePhoto.value = gson.fromJson<ResponsePhoto>(res, ResponsePhoto::class.java)
            }

        })
    }
}