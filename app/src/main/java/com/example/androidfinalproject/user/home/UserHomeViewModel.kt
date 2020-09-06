package com.example.androidfinalproject.user.home

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class UserHomeViewModel @Inject constructor(var userHomeRepository: UserHomeRepository) :
    ViewModel() {
    val userSaldoData = userHomeRepository.userSaldoData
    fun getUserSaldo(id: String) {
        userHomeRepository.getSaldoUser(id)
    }
    fun updateSaldoUser(id:String, userWallet: UserWallet){
        userHomeRepository.updateSaldoUser(id,userWallet)
    }
}