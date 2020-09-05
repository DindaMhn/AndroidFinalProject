package com.example.androidfinalproject.user.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class UserViewModel @Inject constructor(var userRepository: UserRepository) : ViewModel() {

    val userData: LiveData<User> = userRepository.userData
    val userResponse = userRepository.userResponse

    fun loginUser(user: User) {
        userRepository.loginUser(user)

    }

    fun registerUser(user: User) {
        userRepository.registerUser(user)
    }
}