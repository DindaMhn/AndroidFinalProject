package com.example.androidfinalproject.user.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidfinalproject.utils.ResponseData
import javax.inject.Inject

class UserViewModel @Inject constructor(var userRepository: UserRepository) : ViewModel() {

    val userData: LiveData<User> = userRepository.userData
    val userResponse: LiveData<ResponseData> = userRepository.userResponse
    fun loginUser(user: User) {
        userRepository.loginUser(user)
    }

    fun registerUser(user: User) {
        userRepository.registerUser(user)
    }
}