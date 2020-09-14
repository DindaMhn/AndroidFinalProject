package com.example.androidfinalproject.user.ticket

import com.example.androidfinalproject.utils.ResponseData
import retrofit2.Call
import retrofit2.http.*

interface TicketAPI {
    @POST("wallet/payment")
    fun paymentTicket(
        @Header("Authorization") token: String,
        @Body ticket: Ticket
    ): Call<ResponseData>

    @GET("tickets/history?")
    fun historyPayment(
        @Query("page") page: String
        , @Query("limit") limit: String
        , @Query("user_id") user_id: String
        , @Header("Authorization") token: String
    ): Call<ResponseData>

    @DELETE("ticket/{id}")
    fun deleteTicket(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<ResponseData>

    @PUT("ticket/inactive/{id}")
    fun updateTicketStatus(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<ResponseData>

    @GET("ticket/view/{id}")
    fun getTicketViewByID(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<ResponseData>

    @POST("ticket/new")
    fun createTicket(
        @Header("Authorization") token: String,
        @Body ticket: Ticket
    ): Call<ResponseData>

    @PUT("ticket/active/{id}")
    fun updateTicketStatusActive(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<ResponseData>
}