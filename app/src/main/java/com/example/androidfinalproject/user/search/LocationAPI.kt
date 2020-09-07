package com.example.androidfinalproject.user.search

import com.example.androidfinalproject.utils.ResponseData
import com.example.androidfinalproject.utils.ResponseLocation
import retrofit2.Call
import retrofit2.http.GET

interface LocationAPI {
    @GET("providerassets/locations")
    fun getAsset(): Call<ResponseLocation>
}