package com.example.androidfinalproject.user.rating

import com.example.androidfinalproject.utils.ResponseData
import retrofit2.Call
import retrofit2.http.*

interface RatingAPI {
    @GET("review/review/status")
    fun getStatusRating(@Query("user_id")user_id:String
                        , @Query("asset_id")asset_id:String): Call<ResponseData>

    @POST("review/review")
    fun createRating(@Body rating:Rating):Call<ResponseData>

}