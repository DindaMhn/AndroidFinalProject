package com.example.androidfinalproject.user.home

import com.example.androidfinalproject.utils.ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserHomeAPI {
    @GET("user/saldo/{id}")
    fun getSaldoUser(@Path("id") id: String): Call<ResponseData>
}