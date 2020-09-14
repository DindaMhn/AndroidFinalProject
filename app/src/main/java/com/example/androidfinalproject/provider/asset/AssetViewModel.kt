package com.example.androidfinalproject.provider.asset


import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AssetViewModel @Inject constructor(var assetRepository: AssetRepository) :
    ViewModel() {
    val assetList = assetRepository.assetList
    val assetListResponse = assetRepository.assetListResponse
    var reportDaily = assetRepository.reportDaily
    var reportDailyResponse = assetRepository.reportDailyResponse
    var reportMonthly = assetRepository.reportMonthly
    var reportMontlyResponse = assetRepository.reportMontlyResponse

    var assetRating = assetRepository.ratingAsset

    fun getAssetListByProviderID(provider_id: String, token: String) {
        assetRepository.getAssetListByProviderID(provider_id, token)
    }

    fun getReportAssetDaily(id: String, token: String) {
        assetRepository.getReportAssetDaily(id, token)
    }

    fun getReportAssetMonthly(id: String, token: String) {
        assetRepository.getReportAssetMonthly(id, token)
    }

    fun getRatingAsset(provider_id: String, token: String) {
        assetRepository.getRatingAsset(provider_id, token)
    }
}