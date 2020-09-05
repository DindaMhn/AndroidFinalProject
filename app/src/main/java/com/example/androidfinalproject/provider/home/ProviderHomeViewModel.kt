package com.example.androidfinalproject.provider.home

import androidx.lifecycle.ViewModel
import com.example.androidfinalproject.provider.account.ProviderRepository
import javax.inject.Inject

class ProviderHomeViewModel @Inject constructor(var providerHomeRepository: ProviderHomeRepository) :
    ViewModel() {
    val providerData = providerHomeRepository.providerData
    val providerResponse = providerHomeRepository.providerResponse
    val providerAssetData = providerHomeRepository.providerAssetData
    fun getSaldoProvider(id: String) {
        providerHomeRepository.getSaldoProvider(id)
    }
    fun createAsset(asset: Asset){
        providerHomeRepository.createAsset(asset)
    }
}