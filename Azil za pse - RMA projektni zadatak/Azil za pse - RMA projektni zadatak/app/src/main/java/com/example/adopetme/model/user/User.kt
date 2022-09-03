package com.example.adopetme.model.user


data class User(
    var id: String = "",
    val username: String,
    val email: String,
    val password:String,
    var picture: String = "",
    val isAdmin: Boolean = false,
    var hasDog: Boolean = false
) {
}