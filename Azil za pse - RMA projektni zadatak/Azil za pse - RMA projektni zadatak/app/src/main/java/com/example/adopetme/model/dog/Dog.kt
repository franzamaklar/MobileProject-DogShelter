package com.example.adopetme.model.dog

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.URL
import java.net.URLConnection

data class Dog(
    var id: Long? = null,
    val breed: String,
    val name: String,
    val gender: String,
    val age: String,
    val picture: Uri
) {

}