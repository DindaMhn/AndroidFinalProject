package com.example.androidfinalproject.provider.profile

import android.app.Activity
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.profile.UserProfile
import com.example.androidfinalproject.utils.ResponseData
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProviderProfileRepository @Inject constructor(val providerProfileAPI: ProviderProfileAPI) {
    var providerResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    var providerData: MutableLiveData<ProviderProfile> = MutableLiveData<ProviderProfile>()

    fun updateProviderProfile(id: String, token: String, providerUpdate: ProviderUpdate) {
        providerProfileAPI.updateProviderProfile(id, token, providerUpdate)
            .enqueue(object : Callback<ResponseData> {
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    println("RESPONSE " + response.body())
                    val response = response.body()
                    val gson = Gson()
                    val res = gson.toJson(response)
                    val resData = gson.toJson(response?.result)
                    println(resData)
                    providerData.value =
                        gson.fromJson<ProviderProfile>(resData, ProviderProfile::class.java)
                    providerResponse.value =
                        gson.fromJson<ResponseData>(res, ResponseData::class.java)
                }
            })
    }

    fun deleteProviderPhoto(id: String, token: String) {
        providerProfileAPI.deleteProviderPhoto(id, token).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
                providerResponse.value = gson.fromJson<ResponseData>(res, ResponseData::class.java)
            }
        })
    }

    fun getProvider(id: String, token: String) {
        providerProfileAPI.getProvider(id, token).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val resData = gson.toJson(response?.result)
                providerData.value =
                    gson.fromJson<ProviderProfile>(resData, ProviderProfile::class.java)
            }

        })
    }

    fun getProviderPhoto(id: String, token: String, imageContainer: ImageView, activity: Activity) {
        providerProfileAPI.getProviderPhoto(id, token).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() != 404 && response.code() != 403) {
                    val responseImage = BitmapFactory.decodeStream(response.body()!!.byteStream())
                    Glide.with(activity).asBitmap().load(responseImage).centerCrop()
                        .into(imageContainer)
                }
            }
        })
    }

    fun updateProviderPhoto(
        providerId: String,
        token: String,
        photo: MultipartBody.Part,
        id: MultipartBody.Part
    ) {
        providerProfileAPI.updateProviderPhoto(providerId, token, photo, id)
            .enqueue(object : Callback<ResponseData> {
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    val response = response.body()
                    val gson = Gson()
                    val res = gson.toJson(response)
                    providerResponse.value =
                        gson.fromJson<ResponseData>(res, ResponseData::class.java)
                }
            })
    }
}