package com.example.adopetme.repository

import androidx.lifecycle.MutableLiveData
import com.example.adopetme.model.dog.Dog

interface DogRepository {
    fun save(dog: Dog)
    fun delete(dog: Dog)
    fun getDogById(id: Long?): Dog?
    fun getAllDogs(): MutableLiveData<List<Dog>>
}