package com.example.adopetme.ui.profile

import com.example.adopetme.model.dog.DogPhoto

interface OnUploadSelectedListener {
    fun OnUploadSelected(dogPhoto: DogPhoto)
}