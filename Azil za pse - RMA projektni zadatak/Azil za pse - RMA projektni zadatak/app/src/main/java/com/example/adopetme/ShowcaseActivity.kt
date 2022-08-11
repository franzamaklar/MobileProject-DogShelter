package com.example.adopetme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adopetme.databinding.ActivityShowcaseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ShowcaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowcaseBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = Firebase.auth
        binding = ActivityShowcaseBinding.inflate(layoutInflater)
        binding.logoutButton.setOnClickListener { logout() }
        setContentView(binding.root)
    }

    private fun logout() {
        firebaseAuth.signOut()
        startActivity(
            Intent(
                this,
                WelcomeActivity::class.java
            )
        )
    }
}