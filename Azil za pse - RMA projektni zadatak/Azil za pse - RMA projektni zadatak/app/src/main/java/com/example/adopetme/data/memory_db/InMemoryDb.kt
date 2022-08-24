package com.example.adopetme.data.memory_db

import com.example.adopetme.R
import com.example.adopetme.data.DogDao
import com.example.adopetme.model.dog.Dog

class InMemoryDb: DogDao {
    private val dogs = mutableListOf<Dog>()

    init {
        save(Dog(
            1,
            "Corgi",
            "Bradly",
             "M",
            4,
            R.drawable.dogo1
        ))
        save(Dog(
            2,
            "Huski",
            "Lovely",
            "F",
            1,
            R.drawable.dogo2
        ))
        save(Dog(
            3,
            "Dalmatian and Sheppard",
            "Dan and Sun",
            "M & M",
            5,
            R.drawable.dogo3
        ))
    }

    override fun save(dog: Dog){
        dogs.add(dog)
    }

    override fun getDogById(id: Long?): Dog?{
        return dogs.firstOrNull{
            it.id == id
        }
    }

    override fun getAllDogs(): List<Dog>{
        return dogs
    }

    override fun delete(dog: Dog) {
        dogs.remove(dog)
    }
}