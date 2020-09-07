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
    var id: String = "556169b9-eccc-11ea-83bf-b4a9fc958140",
    var username: String = "username",
    var asset_name: String = "MONAS",
    var license_plate: String = "B 3030 PTK",
    var based_fee: String = "2000",
    var parking_duration_hour: String = "123",
    var pay_fee: String = "246000",
    var book_at: String = "2020-09-02 10:28:13",
    var start_at: String = "2020-09-02 10:28:13",
    var finished_at: String = "2020-09-07 12:36:03"
)