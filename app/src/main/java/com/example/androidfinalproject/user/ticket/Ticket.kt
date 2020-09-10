package com.example.androidfinalproject.user.ticket

class Ticket(
    var id: String = "",
    var user_id: String = "",
    var asset_id: String = "",
    var fee_id: String = "",
    var vehicle_id: String = "",
    var license_plate: String = "",
    var book_at: String = "",
    var start_at: String = "",
    var finished_at: String = "",
    var status: String = ""
)

class TicketView(
    var id: String = "",
    var username: String = "",
    var asset_name: String = "",
    var license_plate: String = "",
    var based_fee: String = "",
    var parking_duration_hour: String = "",
    var pay_fee: String = "",
    var book_at: String = "",
    var start_at: String = "",
    var finished_at: String = ""
)