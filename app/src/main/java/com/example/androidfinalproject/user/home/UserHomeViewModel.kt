package com.example.androidfinalproject.user.home

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class UserHomeViewModel @Inject constructor(var userHomeRepository: UserHomeRepository) :
    ViewModel() {
    val userSaldoData = userHomeRepository.userSaldoData
    val userTicket = userHomeRepository.userTicketData
    val userResponse = userHomeRepository.userResponse
    fun getUserSaldo(id: String) {
        userHomeRepository.getSaldoUser(id)
    }
    fun updateSaldoUser(id:String, userWallet: UserWallet){
        userHomeRepository.updateSaldoUser(id,userWallet)
    }
    fun getUserTicket(id:String){
        userHomeRepository.getUserTicket(id)
    }
}