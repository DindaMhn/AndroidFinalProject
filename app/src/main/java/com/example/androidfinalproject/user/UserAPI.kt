package com.example.androidfinalproject.user

import com.example.androidfinalproject.provider.ResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPI {
    @POST("authUser/login")
    fun loginUser(@Body user: User): Call<ResponseDataUser>
    @POST("authUser/register")
    fun registerUser(@Body user: User): Call<ResponseDataUser>
    @GET
    fun getSaldoUser(@Path("id") id: String):Call<ResponseDataUser>
}