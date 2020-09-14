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
    fun updateProviderProfile(id: String, token: String, providerUpdate: ProviderUpdate) {
        providerProfileRepository.updateProviderProfile(id, token, providerUpdate)
    }

    fun deleteProviderPhoto(id: String, token: String) {
        providerProfileRepository.deleteProviderPhoto(id, token)
    }

    fun getById(id: String, token: String) {
        providerProfileRepository.getProvider(id, token)
    }

    fun getProviderPhoto(id: String, token: String, imageContainer: ImageView, activity: Activity) {
        providerProfileRepository.getProviderPhoto(id, token, imageContainer, activity)
    }

    fun updateProviderPhoto(
        providerId: String,
        token: String,
        photo: MultipartBody.Part,
        id: MultipartBody.Part
    ) {
        providerProfileRepository.updateProviderPhoto(providerId, token, photo, id)
    }

}