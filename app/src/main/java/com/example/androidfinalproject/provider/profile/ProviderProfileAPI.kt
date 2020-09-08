package com.example.androidfinalproject.provider.profile

import com.example.androidfinalproject.utils.ResponseData
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ProviderProfileAPI {
    @PUT("provider/data/{id}")
    fun updateProviderProfile(
        @Path("id") id: String,
        @Body providerUpdate: ProviderUpdate
    ): Call<ResponseData>

    @DELETE("provider/photo/{id}")
    fun deleteProviderPhoto(@Path("id") id: String): Call<ResponseData>

    @GET("authProvider/{id}")
    fun getProvider(@Path("id") id: String): Call<ResponseData>

    @GET("provider/photo/{id}")
    fun getProviderPhoto(@Path("id") id: String): Call<ResponseBody>

    @Multipart
    @PUT("provider/photo/{id}")
    fun updateProviderPhoto(
        @Path("id") providerId: String,
        @Part photo: MultipartBody.Part,
        @Part id: MultipartBody.Part
    ): Call<ResponseData>
}