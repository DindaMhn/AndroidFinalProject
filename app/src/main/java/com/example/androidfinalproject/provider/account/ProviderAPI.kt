package com.example.androidfinalproject.provider.account

import com.example.androidfinalproject.provider.account.Provider
import com.example.androidfinalproject.utils.ResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProviderAPI {
    @POST("authProvider/login")
    fun loginProvider(@Body provider: Provider):Call<ResponseData>
    @POST("authProvider/register")
    fun registerProvider(@Body provider: Provider):Call<ResponseData>
//    @GET("provider/saldo/{id}")
//    fun getSaldoProvider(@Path("id") id: String):Call<ResponseData>
}