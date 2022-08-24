package com.example.adopetme.di

import com.example.adopetme.data.DogDao
import com.example.adopetme.data.DogRepository
import com.example.adopetme.data.DogRepositoryImpl
import com.example.adopetme.data.memory_db.InMemoryDb

object DogRepositoryFactory {
    private val dogDao: DogDao = InMemoryDb()
    val dogRepository: DogRepository = DogRepositoryImpl(dogDao)
}