package com.example.androidfinalproject.provider.profile

import com.example.androidfinalproject.utils.ResponseData
import retrofit2.Call
import retrofit2.http.*

interface ProviderProfileAPI {
    @PUT("provider/data/{id}")
    fun updateProviderProfile(@Path("id") id: String, @Body providerUpdate: ProviderUpdate): Call<ResponseData>
    @DELETE("provider/photo/{id}")
    fun deleteProviderPhoto(@Path("id") id: String): Call<ResponseData>
    @GET("authProvider/{id}")
    fun getProvider(@Path("id")id:String):Call<ResponseData>
}