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

    fun updateUserProfile(id: String, userUpdate: UserUpdate) {
        userProfileRepository.updateUserProfile(id, userUpdate)
    }

    fun deleteUserPhoto(id: String) {
        userProfileRepository.deleteUserPhoto(id)
    }

    fun getById(id: String) {
        userProfileRepository.getUser(id)
    }

    fun getUserPhoto(id: String, imageContainer: ImageView, activity: Activity) {
        userProfileRepository.getUserPhoto(id, imageContainer, activity)
    }

    fun updateUserPhoto(userId: String, photo: MultipartBody.Part, id: MultipartBody.Part) {
        userProfileRepository.updateUserPhoto(userId, photo, id)
    }

    fun getTicketById(id: String) {
        userProfileRepository.getTicketById(id)
    }
}