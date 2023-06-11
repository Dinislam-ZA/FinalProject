package com.example.finalproject.data.model

data class User(
    var username:String? = null,
    var password: String? = null,
    var email: String? = null,
)

data class UserVO(
    val id:Long,
    val username:String,
    val email: String,
    val createdAt:String
)

data class LoginRequest(
    var username:String,
    var password: String
)