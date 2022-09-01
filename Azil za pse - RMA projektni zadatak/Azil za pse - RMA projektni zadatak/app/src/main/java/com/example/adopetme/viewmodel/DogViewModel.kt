package com.example.adopetme.viewmodel

import androidx.lifecycle.ViewModel
import com.example.adopetme.repository.DogRepositoryImpl
import com.example.adopetme.model.dog.Dog
import com.example.adopetme.model.dog.DogPhoto
import com.example.adopetme.model.user.User
import com.example.adopetme.repository.DogPhotoRepositoryImpl
import com.example.adopetme.repository.UserRepositoryImpl

class DogViewModel(
    private val dogRepository: DogRepositoryImpl,
    private val photoRepository: DogPhotoRepositoryImpl,
    private val userRepository: UserRepositoryImpl
) : ViewModel() {

    var dogs = dogRepository.getAllDogs()
    var dogPhotos = photoRepository.getAllDogPhotos()
    var users = userRepository.getAllUsers()

    fun saveDog(dog : Dog){
        dogRepository.save(dog)
        dogs = dogRepository.getAllDogs()
    }

    fun deleteDog(dog: Dog){
        dogRepository.delete(dog)
        dogs = dogRepository.getAllDogs()
    }

    fun getDogById(id: Long?): Dog? {
        return dogRepository.getDogById(id)
    }

    fun saveDogPhoto(dogPhoto: DogPhoto){
        photoRepository.save(dogPhoto)
        dogPhotos = photoRepository.getAllDogPhotos()
    }

    fun deleteDogPhoto(dogPhoto: DogPhoto){
        photoRepository.delete(dogPhoto)
        dogPhotos = photoRepository.getAllDogPhotos()
    }

    fun getDogPhotoById(id: Long?): DogPhoto? {
        return photoRepository.getDogPhotoById(id)
    }

    fun saveUser(user: User){
        val result = userRepository.save(user)
        users = userRepository.getAllUsers()
        return result
    }

    fun deleteUser(user: User){
        userRepository.delete(user)
        users = userRepository.getAllUsers()
    }

    fun isUserRegistered(email: String, password: String){
        return userRepository.isRegistered(email, password)
    }

}