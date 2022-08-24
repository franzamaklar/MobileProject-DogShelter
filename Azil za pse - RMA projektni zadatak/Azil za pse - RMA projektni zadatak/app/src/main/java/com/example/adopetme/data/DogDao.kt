package com.example.adopetme.data

import com.example.adopetme.model.dog.Dog

interface DogDao {
    fun save(dog: Dog)
    fun delete(dog: Dog)
    fun getDogById(id: Long?): Dog?
    fun getAllDogs(): List<Dog>
}