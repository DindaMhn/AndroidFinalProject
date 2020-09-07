package com.example.androidfinalproject.user.profile

class UserProfile (
    var id: String = "",
    var id_wallet:String="",
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
class UserUpdate(
    var borndate: String = "",
    var address: String = ""
)
class ResponsePhoto(
    var path:String=""
)