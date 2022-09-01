package com.example.adopetme.repository

import androidx.lifecycle.MutableLiveData
import com.example.adopetme.model.dog.DogPhoto

interface DogPhotoRepository {
    fun save(dogPhoto:DogPhoto)
    fun delete(dogPhoto:DogPhoto)
    fun getDogPhotoById(Id: Long?): DogPhoto?
    fun getAllDogPhotos(): MutableLiveData<List<DogPhoto>>
}