package com.example.androidfinalproject.user

import androidx.lifecycle.MutableLiveData
import com.example.androidfinalproject.provider.ResponseData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(val userAPI: UserAPI) {
    var userData: MutableLiveData<User> = MutableLiveData<User>()
    var userSaldoData:MutableLiveData<UserWallet> = MutableLiveData<UserWallet>()
    var userResponse: MutableLiveData<ResponseDataUser> = MutableLiveData<ResponseDataUser>()

    fun loginUser(user: User) {
        userAPI.loginUser(user).enqueue(object : Callback<ResponseDataUser> {
            override fun onFailure(call: Call<ResponseDataUser>, t: Throwable) {
                println(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseDataUser>,
                response: Response<ResponseDataUser>
            ) {

                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val stringResponseData = Gson().toJson(response?.result)
                val userLoginResponseObject =
                    Gson().fromJson<ResponseDataUser>(stringResponse, ResponseDataUser::class.java)
                val userLoginResponseDataObject =
                    Gson().fromJson<User>(stringResponseData, User::class.java)
                userResponse.value = userLoginResponseObject
                userData.value = userLoginResponseDataObject

            }
        })
    }

    fun registerUser(user: User) {
        userAPI.registerUser(user).enqueue(object : Callback<ResponseDataUser> {
            override fun onFailure(call: Call<ResponseDataUser>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ResponseDataUser>,
                response: Response<ResponseDataUser>
            ) {
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val userRegisterResponseObject =
                    Gson().fromJson<ResponseDataUser>(stringResponse, ResponseDataUser::class.java)
                userResponse.value = userRegisterResponseObject
            }
        })
    }

    fun getSaldoUser(id: String) {
        userAPI.getSaldoUser(id).enqueue(object : Callback<ResponseDataUser> {
            override fun onFailure(call: Call<ResponseDataUser>, t: Throwable) {
                t.printStackTrace()
            }
            override fun onResponse(
                call: Call<ResponseDataUser>,
                response: Response<ResponseDataUser>
            ) {
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val stringResponseData = Gson().toJson(response?.result)
                val userResponseObject =
                    Gson().fromJson<ResponseDataUser>(stringResponse, ResponseDataUser::class.java)
                val userResponseDataObject =
                    Gson().fromJson<UserWallet>(stringResponseData, UserWallet::class.java)
                userResponse.value = userResponseObject
                userSaldoData.value = userResponseDataObject
            }
        })
    }
}