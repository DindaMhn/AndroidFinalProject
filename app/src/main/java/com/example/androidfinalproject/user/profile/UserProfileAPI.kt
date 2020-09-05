package com.example.androidfinalproject.user.profile

import com.example.androidfinalproject.user.account.User
import com.example.androidfinalproject.utils.ResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserProfileAPI {
    @PUT("user/{id}")
    fun updateUserProfile(@Path("id") id: String, @Body user: User): Call<ResponseData>
    @DELETE("user/photo/{id}")
    fun deleteUserPhoto(@Path("id") id: String): Call<ResponseData>
}