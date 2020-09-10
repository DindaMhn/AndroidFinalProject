package com.example.androidfinalproject.user.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class LocationViewModel @Inject constructor(var locationRepository: LocationRepository) :
    ViewModel() {
    val listLocationData : LiveData<List<Location>> = locationRepository.listLocation
    val detailLocationData : LiveData<Location> = locationRepository.detailLocation
    fun getAssetLocation() {
        locationRepository.getLocation()
    }
    fun getDetail(id:String){
        locationRepository.getDateilLocation(id)
    }
}