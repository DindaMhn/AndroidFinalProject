package com.example.androidfinalproject.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ProviderViewModel @Inject constructor(var providerRepository: ProviderRepository):ViewModel(){

    val providerData = providerRepository.providerData
    val providerResponse = providerRepository.providerResponse

    fun loginProvider(provider: Provider){
        providerRepository.loginProvider(provider)
    }
    fun registerProvider(provider: Provider){
        providerRepository.registerProvider(provider)
    }
}