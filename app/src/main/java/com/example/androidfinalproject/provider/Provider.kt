package com.example.androidfinalproject.provider

class Provider (
    var id: String = "",
    var username: String = "",
    var password: String = "",
    var email: String = "",
    var fullname: String = "",
    var photo: String = "",
    var bornDate: String = "",
    var phoneNumber: String = "",
    var address: String = "",
    var createdAt: String = "",
    var editedAt: String = "",
    var deletedAt: String = "",
    var status: String = ""
)

class ResponseData (
    var message: String = "",
    var status: String = "",
    var token: String = "",
    var result: Any
)