package com.example.androidfinalproject.provider.asset

import androidx.lifecycle.MutableLiveData
import com.example.androidfinalproject.provider.home.AssetDailyView
import com.example.androidfinalproject.provider.home.AssetMonthlyView
import com.example.androidfinalproject.provider.home.AssetRating
import com.example.androidfinalproject.provider.home.AssetView
import com.example.androidfinalproject.utils.ResponseData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AssetRepository @Inject constructor(val assetAPI: AssetAPI) {
    var assetList: MutableLiveData<List<AssetView>> = MutableLiveData<List<AssetView>>()
    var assetListResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()

    var reportDaily: MutableLiveData<List<AssetDailyView>> = MutableLiveData<List<AssetDailyView>>()
    var reportDailyResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()

    var reportMonthly: MutableLiveData<List<AssetMonthlyView>> =
        MutableLiveData<List<AssetMonthlyView>>()
    var reportMontlyResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()

    var ratingAssetResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    var ratingAsset: MutableLiveData<List<AssetRating>> = MutableLiveData<List<AssetRating>>()


    fun getAssetListByProviderID(provider_id: String) {
        assetAPI.getListAssetByProviderID(provider_id).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
                val resData = gson.toJson(response?.result)
                assetListResponse.value = gson.fromJson<ResponseData>(res, ResponseData::class.java)
                if (resData != "null") {
                    assetList.value =
                        gson.fromJson<Array<AssetView>>(resData, Array<AssetView>::class.java)
                            .toList()
                } else {
                    assetList.value = listOf(AssetView("", "", "", "", "", "", "", "", "", "", ""))
                }
            }

        })
    }

    fun getReportAssetDaily(id: String) {
        assetAPI.getReportAssetDaily(id).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
                val resData = gson.toJson(response?.result)

                reportDailyResponse.value =
                    gson.fromJson<ResponseData>(res, ResponseData::class.java)
                if (resData != "null") {
                    reportDaily.value = gson.fromJson<Array<AssetDailyView>>(
                        resData,
                        Array<AssetDailyView>::class.java
                    ).toList()

                } else {
                    reportDaily.value = listOf(AssetDailyView("", "", "", ""))
                }
            }

        })
    }

    fun getReportAssetMonthly(id: String) {
        assetAPI.getReportAssetMonthly(id).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
                val resData = gson.toJson(response?.result)
                reportMontlyResponse.value =
                    gson.fromJson<ResponseData>(res, ResponseData::class.java)
                if (resData != "null") {
                    reportMonthly.value = gson.fromJson<Array<AssetMonthlyView>>(
                        resData,
                        Array<AssetMonthlyView>::class.java
                    ).toList()
                } else {
                    reportMonthly.value = listOf(AssetMonthlyView("", "", "", ""))
                }


            }

        })

    }

    fun getRatingAsset(provider_id: String) {
        assetAPI.getRatingAsset(provider_id).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
                val resData = gson.toJson(response?.result)
                ratingAssetResponse.value =
                    gson.fromJson<ResponseData>(res, ResponseData::class.java)
                if (response?.status!=400.toString()) {
                    ratingAsset.value = gson.fromJson<Array<AssetRating>>(
                        resData,
                        Array<AssetRating>::class.java
                    ).toList()
                } else {
                    ratingAsset.value = listOf(AssetRating("", "", "", ""))
                }
            }

        })
    }
}