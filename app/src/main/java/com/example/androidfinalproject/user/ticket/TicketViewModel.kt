package com.example.androidfinalproject.user.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidfinalproject.utils.ResponseData
import javax.inject.Inject

class TicketViewModel @Inject constructor(var ticketRepository: TicketRepository): ViewModel(){
    val ticketData: LiveData<Ticket> = ticketRepository.ticketData
    val ticketResponse: LiveData<ResponseData> = ticketRepository.ticketResponse
    val ticketView: LiveData<TicketView> = ticketRepository.ticketView
    val ticketViewResponse: LiveData<ResponseData> = ticketRepository.ticketViewResponse

    val historyPaymentList: LiveData<List<TicketView>> = ticketRepository.historyPaymentList
    val historyPaymentResponse: LiveData<ResponseData> = ticketRepository.historyPaymentResponse
    fun paymentTicket(ticket: Ticket){
        ticketRepository.paymentTicket(ticket)
    }
    fun historyPayment(user_id:String){
        ticketRepository.historyPayment(user_id)
    }
    fun deleteTicket(id: String){
        ticketRepository.deleteTicket(id)
    }
    fun updateTicketStatus(id: String){
        ticketRepository.updateTicketStatus(id)
    }
    fun getTicketViewByID(id:String){
        ticketRepository.getTicketViewByID(id)
    }
}