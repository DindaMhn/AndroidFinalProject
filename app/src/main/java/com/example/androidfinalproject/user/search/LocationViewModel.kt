package com.example.androidfinalproject.user.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class LocationViewModel @Inject constructor(var locationRepository: LocationRepository) :
    ViewModel() {
    val listLocationData: LiveData<List<Location>> = locationRepository.listLocation
    val detailLocationData: LiveData<Location> = locationRepository.detailLocation
    fun getAssetLocation(token: String) {
        locationRepository.getLocation(token)
    }

    fun getDetail(id: String, token: String) {
        locationRepository.getDateilLocation(id, token)
    }
}