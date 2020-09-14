package com.example.androidfinalproject.user.home

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class UserHomeViewModel @Inject constructor(var userHomeRepository: UserHomeRepository) :
    ViewModel() {
    val userSaldoData = userHomeRepository.userSaldoData
    val userTicket = userHomeRepository.userTicketData
    val userResponse = userHomeRepository.userResponse


    fun getUserSaldo(id: String, token: String) {
        userHomeRepository.getSaldoUser(id, token)
    }

    fun updateSaldoUser(id: String, token: String, userWallet: UserWallet) {
        userHomeRepository.updateSaldoUser(id, token, userWallet)
    }

    fun getUserTicket(id: String, token: String) {
        userHomeRepository.getUserTicket(id, token)
    }
}