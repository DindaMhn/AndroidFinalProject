package com.example.androidfinalproject.provider.asset

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AssetViewModel @Inject constructor(var assetRepository: AssetRepository):
    ViewModel(){
    val assetList = assetRepository.assetList
    val assetListResponse = assetRepository.assetListResponse
    fun getAssetListByProviderID(provider_id: String){
        assetRepository.getAssetListByProviderID(provider_id)
    }
}