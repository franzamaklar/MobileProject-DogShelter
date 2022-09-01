package com.example.adopetme.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adopetme.databinding.ActivityWelcomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAuth = Firebase.auth
        if(firebaseAuth.currentUser != null){
            startActivity(Intent(this, ShowcaseActivity::class.java))
            finish()
        }else {
            super.onCreate(savedInstanceState)
            binding = ActivityWelcomeBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }
    }
}