package com.example.adopetme.repository

import androidx.lifecycle.MutableLiveData
import com.example.adopetme.model.user.User

interface UserRepository {
    fun save(user: User)
    fun delete(user: User)
    fun getAllUsers(): MutableLiveData<List<User>>
    fun isRegistered(email: String, password:String)
}