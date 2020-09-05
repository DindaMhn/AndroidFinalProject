package com.example.androidfinalproject.user.profile

import androidx.lifecycle.ViewModel
import com.example.androidfinalproject.user.account.User
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(var userProfileRepository: UserProfileRepository) :
    ViewModel() {
    val userData = userProfileRepository.userData
    fun updateSaldoUser(id: String, user: User) {
        userProfileRepository.updateUserProfile(id, user)
    }
    fun deleteUserPhoto(id:String){
        userProfileRepository.deleteUserPhoto(id)
    }
}