package com.example.androidfinalproject.provider.asset

import com.example.androidfinalproject.utils.ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AssetAPI {
    @GET("providerassets/{id}")
    fun getListAssetByProviderID(@Path("id")id: String): Call<ResponseData>
}