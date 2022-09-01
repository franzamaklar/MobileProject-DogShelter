package com.example.adopetme.model.user

import java.net.URI

data class User(
    var id: String = "",
    val username: String,
    val email: String,
    val password:String,
    val picture: String = "",
    val isAdmin: Boolean = false,
    val hasDog: Boolean = false
) {
}