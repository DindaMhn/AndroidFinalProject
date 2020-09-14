package com.example.androidfinalproject.user.search

import com.example.androidfinalproject.utils.ResponseData
import com.example.androidfinalproject.utils.ResponseLocation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface LocationAPI {
    @GET("providerassets/locations")
    fun getAsset(@Header("Authorization") token: String): Call<ResponseLocation>

    @GET("providerassets/{id}")
    fun getAssetByID(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<ResponseLocation>
}