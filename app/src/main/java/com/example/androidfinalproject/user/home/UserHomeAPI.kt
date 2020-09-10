package com.example.androidfinalproject.user.home

import com.example.androidfinalproject.user.account.User
import com.example.androidfinalproject.utils.ResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserHomeAPI {
    @GET("user/saldo/{id}")
    fun getSaldoUser(@Path("id") id: String): Call<ResponseData>

    @PUT("user/saldo/{id}")
    fun updateSaldoUser(@Path("id") id: String, @Body userWallet: UserWallet): Call<ResponseData>

    @GET("user/ticket/{id}")
    fun getUserTicket(@Path("id") id: String): Call<ResponseData>
}