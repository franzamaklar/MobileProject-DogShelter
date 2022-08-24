package com.example.adopetme.model.dog

import android.graphics.Picture

data class Dog(
    var id: Long? = null,
    val breed: String,
    val name: String,
    val gender: String,
    val age: Int,
    val picture: Int
) {

}