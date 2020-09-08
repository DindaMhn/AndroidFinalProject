package com.example.androidfinalproject.user.profile

import com.example.androidfinalproject.user.account.User
import com.example.androidfinalproject.utils.ResponseData
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserProfileAPI {
    @PUT("user/{id}")
    fun updateUserProfile(@Path("id") id: String, @Body userUpdate: UserUpdate): Call<ResponseData>

    @DELETE("user/photo/{id}")
    fun deleteUserPhoto(@Path("id") id: String): Call<ResponseData>

    @GET("authUser/{id}")
    fun getUser(@Path("id") id: String): Call<ResponseData>

    @GET("user/photo/{id}")
    fun getUserPhoto(@Path("id") id: String): Call<ResponseBody>

    @Multipart
    @PUT("user/photo/{id}")
    fun updateUserPhoto(
        @Path("id") userId: String,
        @Part photo: MultipartBody.Part,
        @Part id: MultipartBody.Part
    ): Call<ResponseData>
}