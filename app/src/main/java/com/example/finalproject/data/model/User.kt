package com.example.finalproject.data.model

data class User(
    var username:String? = null,
    var password: String? = null,
    var phonenumber: String? = null,
)

data class LoginRequest(
    var username:String? = null,
    var password: String? = null,
)