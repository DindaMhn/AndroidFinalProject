package com.example.androidfinalproject.provider.account

import androidx.lifecycle.ViewModel
import com.example.androidfinalproject.provider.account.Provider
import com.example.androidfinalproject.provider.account.ProviderRepository
import javax.inject.Inject

class ProviderViewModel @Inject constructor(var providerRepository: ProviderRepository) :
    ViewModel() {

    val providerData = providerRepository.providerData
    val providerResponse = providerRepository.providerResponse

    fun loginProvider(provider: Provider) {
        providerRepository.loginProvider(provider)
    }

    fun registerProvider(provider: Provider) {
        providerRepository.registerProvider(provider)
    }

//    fun getSaldoProvider(id: String) {
//        providerRepository.getSaldoProvider(id)
//    }
}