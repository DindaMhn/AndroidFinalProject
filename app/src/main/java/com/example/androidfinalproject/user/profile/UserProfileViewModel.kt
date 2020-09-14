package com.example.androidfinalproject.user.profile

import android.app.Activity
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidfinalproject.user.account.User
import com.example.androidfinalproject.utils.ResponseData
import okhttp3.MultipartBody
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(var userProfileRepository: UserProfileRepository) :
    ViewModel() {
    val userData: MutableLiveData<UserProfile> = userProfileRepository.userData
    val userResponse: MutableLiveData<ResponseData> = userProfileRepository.userResponse

    fun updateUserProfile(id: String, token: String, userUpdate: UserUpdate) {
        userProfileRepository.updateUserProfile(id, token, userUpdate)
    }

    fun deleteUserPhoto(id: String, token: String) {
        userProfileRepository.deleteUserPhoto(id, token)
    }

    fun getById(id: String, token: String) {
        userProfileRepository.getUser(id, token)
    }

    fun getUserPhoto(id: String, token: String, imageContainer: ImageView, activity: Activity) {
        userProfileRepository.getUserPhoto(id, token, imageContainer, activity)
    }

    fun updateUserPhoto(
        userId: String,
        token: String,
        photo: MultipartBody.Part,
        id: MultipartBody.Part
    ) {
        userProfileRepository.updateUserPhoto(userId, token, photo, id)
    }

    fun getTicketById(id: String, token: String) {
        userProfileRepository.getTicketById(id, token)
    }
}