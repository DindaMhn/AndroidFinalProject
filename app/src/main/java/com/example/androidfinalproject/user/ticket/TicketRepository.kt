package com.example.androidfinalproject.user.ticket

import androidx.lifecycle.MutableLiveData
import com.example.androidfinalproject.utils.ResponseData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class TicketRepository @Inject constructor(val ticketAPI: TicketAPI){
    var ticketResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    var ticketData: MutableLiveData<Ticket> = MutableLiveData<Ticket>()
    var historyPaymentResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    var historyPaymentList : MutableLiveData<List<TicketView>> = MutableLiveData<List<TicketView>>()
    var deleteTicketResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    var updateTicketResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    var ticketViewResponse: MutableLiveData<ResponseData> = MutableLiveData<ResponseData>()
    var ticketView: MutableLiveData<TicketView> = MutableLiveData<TicketView>()
    fun paymentTicket(ticket: Ticket){
        ticketAPI.paymentTicket(ticket).enqueue(object : Callback<ResponseData>{
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
                val resData = gson.toJson(response?.result)
                ticketResponse.value =  gson.fromJson<ResponseData>(res, ResponseData::class.java)
                ticketData.value = gson.fromJson<Ticket>(resData, Ticket::class.java)
            }

        })
    }

    fun historyPayment(user_id: String){
        ticketAPI.historyPayment("0","10",user_id).enqueue(object : Callback<ResponseData>{
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
                val resData = gson.toJson(response?.result)
                historyPaymentResponse.value =  gson.fromJson<ResponseData>(res, ResponseData::class.java)
                historyPaymentList.value = gson.fromJson<Array<TicketView>>(resData, Array<TicketView>::class.java).toList()
            }

        })
    }

    fun deleteTicket(id: String){
        ticketAPI.deleteTicket(id).enqueue(object :Callback<ResponseData>{
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
//                val resData = gson.toJson(response?.result)
                deleteTicketResponse.value =  gson.fromJson<ResponseData>(res, ResponseData::class.java)
            }

        })
    }

    fun updateTicketStatus(id: String){
        ticketAPI.updateTicketStatus(id).enqueue(object :Callback<ResponseData>{
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
//                val resData = gson.toJson(response?.result)
                updateTicketResponse.value =  gson.fromJson<ResponseData>(res, ResponseData::class.java)
            }

        })
    }

    fun getTicketViewByID(id:String){
        ticketAPI.getTicketViewByID(id).enqueue(object :Callback<ResponseData>{
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val response = response.body()
                val gson = Gson()
                val res = gson.toJson(response)
                val resData = gson.toJson(response?.result)
                ticketViewResponse.value =  gson.fromJson<ResponseData>(res, ResponseData::class.java)
                ticketView.value = gson.fromJson<TicketView>(resData, TicketView::class.java)
            }

        })
    }

}