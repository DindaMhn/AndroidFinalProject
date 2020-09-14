package com.example.androidfinalproject.provider.home

import com.example.androidfinalproject.utils.ResponseData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ProviderHomeAPI {
    @GET("provider/saldo/{id}")
    fun getSaldoProvider(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<ResponseData>

//    @POST("provider/asset")
//    fun createAsset(
//        @Header("Authorization") token: String,
//        @Body asset: Asset
//    ): Call<ResponseData>

    @Multipart
    @POST("provider/asset")
    fun createAsset(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,
        @Part result: MultipartBody.Part
    ): Call<ResponseData>
}