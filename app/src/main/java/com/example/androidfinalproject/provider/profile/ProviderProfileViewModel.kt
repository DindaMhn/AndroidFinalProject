package com.example.androidfinalproject.provider.profile

import android.app.Activity
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

class ProviderProfileViewModel @Inject constructor(var providerProfileRepository: ProviderProfileRepository) :
    ViewModel() {
    val providerData: MutableLiveData<ProviderProfile> = providerProfileRepository.providerData
    fun updateProviderProfile(id: String, providerUpdate: ProviderUpdate) {
        providerProfileRepository.updateProviderProfile(id, providerUpdate)
    }

    fun deleteProviderPhoto(id: String) {
        providerProfileRepository.deleteProviderPhoto(id)
    }

    fun getById(id: String) {
        providerProfileRepository.getProvider(id)
    }

    fun getProviderPhoto(id: String, imageContainer: ImageView, activity: Activity) {
        providerProfileRepository.getProviderPhoto(id, imageContainer, activity)
    }

    fun updateProviderPhoto(providerId: String, photo: MultipartBody.Part, id: MultipartBody.Part) {
        providerProfileRepository.updateProviderPhoto(providerId, photo, id)
    }

}