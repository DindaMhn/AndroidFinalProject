package com.example.androidfinalproject.user.account

import com.example.androidfinalproject.utils.ResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {
    @POST("authUser/login")
    fun loginUser(@Body user: User): Call<ResponseData>
    @POST("authUser/register")
    fun registerUser(@Body user: User): Call<ResponseData>
}