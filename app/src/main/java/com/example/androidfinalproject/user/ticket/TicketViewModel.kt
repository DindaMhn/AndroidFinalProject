package com.example.androidfinalproject.user.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.androidfinalproject.utils.ResponseData
import javax.inject.Inject

class TicketViewModel @Inject constructor(var ticketRepository: TicketRepository) : ViewModel() {
    val ticketData: LiveData<Ticket> = ticketRepository.ticketData
    val ticketResponse: LiveData<ResponseData> = ticketRepository.ticketResponse
    val ticketView: LiveData<TicketView> = ticketRepository.ticketView
    val ticketViewResponse: LiveData<ResponseData> = ticketRepository.ticketViewResponse

    val historyPaymentList: LiveData<List<TicketView>> = ticketRepository.historyPaymentList
    val historyPaymentResponse: LiveData<ResponseData> = ticketRepository.historyPaymentResponse
    fun paymentTicket(token: String, ticket: Ticket) {
        ticketRepository.paymentTicket(token, ticket)
    }

    fun historyPayment(user_id: String, token: String) {
        ticketRepository.historyPayment(user_id, token)
    }

    fun deleteTicket(id: String, token: String) {
        ticketRepository.deleteTicket(id, token)
//        historyPaymentList.
    }

    fun updateTicketStatus(id: String, token: String) {
        ticketRepository.updateTicketStatus(id, token)
    }

    fun getTicketViewByID(id: String, token: String) {
        ticketRepository.getTicketViewByID(id, token)
    }

    fun createTicket(token: String, ticket: Ticket) {
        ticketRepository.createTicket(token, ticket)
    }

    fun updateTicketStatusActive(id: String, token: String) {
        ticketRepository.updateTicketStatusActive(id, token)
    }
}