package com.example.androidfinalproject.user.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidfinalproject.user.account.User
import com.example.androidfinalproject.utils.ResponseData
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(var userProfileRepository: UserProfileRepository) :
    ViewModel() {
    val userData: MutableLiveData<UserProfile> = userProfileRepository.userData
    val userResponse: MutableLiveData<ResponseData> = userProfileRepository.userResponse
    val userResponsePhoto:  MutableLiveData<ResponsePhoto> = userProfileRepository.userResponsePhoto

    fun updateUserProfile(id: String, userUpdate: UserUpdate) {
        userProfileRepository.updateUserProfile(id, userUpdate)
    }
    fun deleteUserPhoto(id:String){
        userProfileRepository.deleteUserPhoto(id)
    }
    fun getById(id:String){
        userProfileRepository.getUser(id)
    }
    fun getUserPhoto(id:String){
        userProfileRepository.getUserPhoto(id)
    }
}