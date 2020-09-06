package com.example.androidfinalproject.provider.profile

class ProviderProfile (
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
class ProviderUpdate(
    var borndate: String = "",
    var address: String = ""
)