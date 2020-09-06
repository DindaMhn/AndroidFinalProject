package com.example.androidfinalproject.provider.profile

class ProviderProfile (
    var id: String = "",
    var username: String = "",
    var password: String = "",
    var email: String = "",
    var fullname: String = "",
    var photo: String = "",
    var borndate: String = "",
    var phone_number: String = "",
    var address: String = "",
    var created_at: String = "",
    var edited_at: String = "",
    var deleted_at: String = "",
    var status: String = ""
)
class ProviderUpdate(
    var borndate: String = "",
    var address: String = ""
)