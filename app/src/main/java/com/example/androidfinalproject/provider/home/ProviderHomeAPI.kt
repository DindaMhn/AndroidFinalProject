package com.example.androidfinalproject.provider.home

import com.example.androidfinalproject.utils.ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProviderHomeAPI {
    @GET("provider/saldo/{id}")
    fun getSaldoProvider(@Path("id") id: String): Call<ResponseData>
}