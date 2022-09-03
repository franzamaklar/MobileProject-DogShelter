package com.example.adopetme.repository

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.adopetme.model.dog.Dog
import com.example.adopetme.model.user.User

interface UserRepository {
    fun save(user: User)
    fun update(user: User, dog: Dog)
    fun update(user: User, picture: Uri)
    fun getUserById(Id: String?): User?
    fun getAllUsers(): MutableLiveData<MutableList<User>>
    fun isRegistered(email: String, password:String)
    fun getUserDog(user: User): MutableLiveData<Dog>?
}