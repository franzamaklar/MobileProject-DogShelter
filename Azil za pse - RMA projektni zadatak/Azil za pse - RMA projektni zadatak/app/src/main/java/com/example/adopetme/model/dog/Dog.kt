package com.example.adopetme.model.dog


import android.net.Uri


data class Dog(
    var id: Long? = null,
    val breed: String,
    val name: String,
    val gender: String,
    val age: String,
    val picture: Uri
) {

}