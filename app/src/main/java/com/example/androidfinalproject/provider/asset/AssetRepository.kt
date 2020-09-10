package com.example.androidfinalproject.provider.asset

import androidx.lifecycle.MutableLiveData
import com.example.androidfinalproject.provider.home.Asset
import com.example.androidfinalproject.utils.ResponseData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AssetRepository @Inject constructor(val assetAPI: AssetAPI){
    var assetList : MutableLiveData<List<Asset>> = MutableLiveData<List<Asset>>()
    var assetListResponse : MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()

    fun getAssetListByProviderID(provider_id: String){
        assetAPI.getListAssetByProviderID(provider_id).enqueue(object : Callback<ResponseData>{
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
                val resData = gson.toJson(response?.result)
                assetListResponse.value =  gson.fromJson<ResponseData>(res, ResponseData::class.java)
                assetList.value = gson.fromJson<Array<Asset>>(resData, Array<Asset>::class.java).toList()
            }

        })
    }
}