package com.example.androidfinalproject.user.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidfinalproject.user.profile.UserProfile
import com.example.androidfinalproject.user.profile.UserProfileRepository
import javax.inject.Inject

class LocationViewModel @Inject constructor(var locationRepository: LocationRepository) :
    ViewModel() {
    val listLocationData : LiveData<List<Location>> = locationRepository.listLocation

    fun getLocation(){
        locationRepository.getLocation()
    }
}