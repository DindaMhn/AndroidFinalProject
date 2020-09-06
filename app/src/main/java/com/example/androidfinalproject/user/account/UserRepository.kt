package com.example.androidfinalproject.user.account

import androidx.lifecycle.MutableLiveData
import com.example.androidfinalproject.utils.ResponseData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(val userAPI: UserAPI) {
    var userData: MutableLiveData<User> = MutableLiveData<User>()
    var userResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    var id: String = ""
    fun loginUser(user: User) {
        userAPI.loginUser(user).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                println(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {

                val response = response.body()
                if (response?.message == "Success") {
                    val gson = Gson()
//                userResponse.value = userLoginResponseObject
                    val res = gson.toJson(response)
                    val resData = gson.toJson(response?.result)
                    userData.value = gson.fromJson<User>(resData, User::class.java)
                    userResponse.value = gson.fromJson<ResponseData>(res, ResponseData::class.java)
                    println("ID FROM REPO" + userData.value!!.id)
                    id = userData.value!!.id
                } else{
                    println("DATA NOT FOUND")
                }
            }
//                val stringResponse = Gson().toJson(response)
//                val stringResponseData = Gson().toJson(response?.result)
//                val userLoginResponseObject =
//                    Gson().fromJson<ResponseData>(stringResponse, ResponseData::class.java)
//                val userLoginResponseDataObject =
//                    Gson().fromJson<User>(stringResponseData, User::class.java)

        })
    }

    fun registerUser(user: User) {
        userAPI.registerUser(user).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val userRegisterResponseObject =
                    Gson().fromJson<ResponseData>(stringResponse, ResponseData::class.java)
                userResponse.value = userRegisterResponseObject
            }
        })
    }

}