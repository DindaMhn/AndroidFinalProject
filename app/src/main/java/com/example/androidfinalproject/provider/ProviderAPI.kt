package com.example.androidfinalproject.provider

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ProviderAPI {
    @POST("authProvider/login")
    fun loginProvider(@Body provider: Provider):Call<ResponseData>
    @POST("authProvider/register")
    fun registerProvider(@Body provider: Provider):Call<ResponseData>
}