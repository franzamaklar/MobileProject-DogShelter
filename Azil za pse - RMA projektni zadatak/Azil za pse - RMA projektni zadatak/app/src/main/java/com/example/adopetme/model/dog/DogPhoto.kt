package com.example.adopetme.model.dog

import android.net.Uri

data class DogPhoto(
    var picture: Uri,
    var id: Long? = 0
) {
}