package com.example.adopetme.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.adopetme.repository.DogRepositoryImpl
import com.example.adopetme.model.dog.Dog
import com.example.adopetme.model.dog.DogPhoto
import com.example.adopetme.model.user.User
import com.example.adopetme.repository.DogPhotoRepositoryImpl
import com.example.adopetme.repository.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DogViewModel(
    private val dogRepository: DogRepositoryImpl,
    private val photoRepository: DogPhotoRepositoryImpl,
    private val userRepository: UserRepositoryImpl
) : ViewModel() {

    var firebaseAuth:FirebaseAuth = Firebase.auth

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

    fun saveUser(user: User){
        val result = userRepository.save(user)
        users = userRepository.getAllUsers()
        return result
    }

    fun updateUser(user: User, dog: Dog){
        userRepository.update(user, dog)
        users = userRepository.getAllUsers()
    }

    fun updateUser(user: User, picture: Uri){
        userRepository.update(user, picture)
        users = userRepository.getAllUsers()
    }

    fun isUserRegistered(email: String, password: String){
        return userRepository.isRegistered(email, password)
    }

    fun getUserById(): User?{
        return userRepository.getUserById(firebaseAuth.currentUser!!.uid)
    }

    fun getUserDog(user: User): LiveData<Dog>?{
        return userRepository.getUserDog(user)
    }


}