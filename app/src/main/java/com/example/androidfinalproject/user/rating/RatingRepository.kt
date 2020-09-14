package com.example.androidfinalproject.user.rating

import androidx.lifecycle.MutableLiveData
import com.example.androidfinalproject.utils.ResponseData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RatingRepository @Inject constructor(val ratingAPI: RatingAPI) {
    var statusRatingResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    var statusRatingData: MutableLiveData<String> = MutableLiveData<String>()

    var createRatingResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    fun getStatusRating(user_id: String, asset_id: String, token: String) {
        ratingAPI.getStatusRating(user_id, asset_id, token)
            .enqueue(object : Callback<ResponseData> {
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    val response = response.body()
                    val gson = Gson()
                    val res = gson.toJson(response)
                    val resData = gson.toJson(response?.result)
                    statusRatingData.value = gson.fromJson<String>(resData, String::class.java)
                    statusRatingResponse.value =
                        gson.fromJson<ResponseData>(res, ResponseData::class.java)
                }

            })
    }

    fun createRating(token: String, rating: Rating) {
        ratingAPI.createRating(token, rating).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
//                val resData = gson.toJson(response?.result)
//                statusRatingData.value = gson.fromJson<String>(resData, String::class.java)
                createRatingResponse.value =
                    gson.fromJson<ResponseData>(res, ResponseData::class.java)
            }

        })

    }
}