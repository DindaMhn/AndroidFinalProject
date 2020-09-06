package com.example.androidfinalproject.user.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidfinalproject.user.account.User
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(var userProfileRepository: UserProfileRepository) :
    ViewModel() {
    val userData: MutableLiveData<UserProfile> = userProfileRepository.userData
    fun updateUserProfile(id: String, userUpdate: UserUpdate) {
        userProfileRepository.updateUserProfile(id, userUpdate)
    }
    fun deleteUserPhoto(id:String){
        userProfileRepository.deleteUserPhoto(id)
    }
    fun getById(id:String){
        userProfileRepository.getUser(id)
    }
}