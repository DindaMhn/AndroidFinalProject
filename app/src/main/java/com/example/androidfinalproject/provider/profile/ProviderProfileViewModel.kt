package com.example.androidfinalproject.provider.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ProviderProfileViewModel @Inject constructor(var providerProfileRepository: ProviderProfileRepository):ViewModel(){
    val providerData: MutableLiveData<ProviderProfile> = providerProfileRepository.providerData
    fun updateProviderProfile(id: String,providerUpdate: ProviderUpdate) {
        providerProfileRepository.updateProviderProfile(id, providerUpdate)
    }
    fun deleteProviderPhoto(id:String){
        providerProfileRepository.deleteProviderPhoto(id)
    }
    fun getById(id:String){
        providerProfileRepository.getProvider(id)
    }
}