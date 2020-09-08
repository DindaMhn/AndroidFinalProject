package com.example.androidfinalproject.user.ticket

import com.example.androidfinalproject.utils.ResponseData
import retrofit2.Call
import retrofit2.http.*

interface TicketAPI {
    @POST("wallet/payment")
    fun paymentTicket(@Body ticket: Ticket): Call<ResponseData>

    @GET("tickets/history?")
    fun historyPayment(
        @Query("page") page: String
        , @Query("limit") limit: String
        , @Query("user_id") user_id: String
    ): Call<ResponseData>

    @DELETE("ticket/{id}")
    fun deleteTicket(@Path("id") id: String): Call<ResponseData>

    @PUT("ticket/inactive/{id}")
    fun updateTicketStatus(@Path("id") id: String): Call<ResponseData>

    @GET("ticket/{id}")
    fun getTicketViewByID(@Path("id") id: String): Call<ResponseData>
}