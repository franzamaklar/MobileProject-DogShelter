package com.example.adopetme.data

import com.example.adopetme.model.dog.Dog

class DogRepositoryImpl(val dogDao: DogDao): DogRepository {
    override fun save(dog: Dog) {
        dogDao.save(dog)
    }

    override fun delete(dog: Dog) {
        dogDao.delete(dog)
    }

    override fun getDogById(id: Long?): Dog? {
       return dogDao.getDogById(id)
    }

    override fun getAllDogs(): List<Dog> {
        return dogDao.getAllDogs()
    }
}