package com.example.androidfinalproject.user.profile

import android.app.Activity
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.androidfinalproject.R
import com.example.androidfinalproject.utils.ResponseData
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserProfileRepository @Inject constructor(val userProfileAPI: UserProfileAPI) {
    var userResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    var userData: MutableLiveData<UserProfile> = MutableLiveData<UserProfile>()

    fun updateUserProfile(id: String, token: String, userUpdate: UserUpdate) {
        userProfileAPI.updateUserProfile(id, token, userUpdate)
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
                    val resData = gson.toJson(response?.result)
                    userData.value = gson.fromJson<UserProfile>(resData, UserProfile::class.java)
                    userResponse.value = gson.fromJson<ResponseData>(res, ResponseData::class.java)
                }
            })
    }

    fun deleteUserPhoto(id: String, token: String) {
        userProfileAPI.deleteUserPhoto(id, token).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
                userResponse.value = gson.fromJson<ResponseData>(res, ResponseData::class.java)
            }
        })
    }

    fun getUser(id: String, token: String) {
        userProfileAPI.getUser(id, token).enqueue(object : Callback<ResponseData> {
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

    fun getUserPhoto(id: String, token: String, imageContainer: ImageView, activity: Activity) {
        userProfileAPI.getUserPhoto(id, token).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() != 404 && response.code() != 403) {
                    val responseImage =
                        BitmapFactory.decodeStream(response.body()!!.byteStream())
                    Glide.with(activity).asBitmap().load(responseImage).centerCrop()
                        .into(imageContainer)
                }
            }

        })
    }

    fun updateUserPhoto(
        userId: String,
        token: String,
        photo: MultipartBody.Part,
        id: MultipartBody.Part
    ) {
        userProfileAPI.updateUserPhoto(userId, token, photo, id)
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
                    userResponse.value = gson.fromJson<ResponseData>(res, ResponseData::class.java)
                }
            })
    }

    fun getTicketById(id: String, token: String) {
        userProfileAPI.getUserTicketById(id, token).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
                userResponse.value = gson.fromJson<ResponseData>(res, ResponseData::class.java)
            }

        })
    }
}