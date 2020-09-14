package com.example.androidfinalproject.user.rating

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.androidfinalproject.utils.ResponseData
import javax.inject.Inject


class RatingViewModel @Inject constructor(var ratingRepository: RatingRepository) : ViewModel() {

    val statusRatingData: LiveData<String> = ratingRepository.statusRatingData
    val statusRatingResponse: LiveData<ResponseData> = ratingRepository.statusRatingResponse

    val createRatingResponse: LiveData<ResponseData> = ratingRepository.createRatingResponse

    fun getStatusRating(user_id: String, asset_id: String, token: String) {
        ratingRepository.getStatusRating(user_id, asset_id, token)
    }

    fun createRating(token: String, rating: Rating) {
        ratingRepository.createRating(token, rating)
    }
}