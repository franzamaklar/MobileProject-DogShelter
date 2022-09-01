package com.example.adopetme.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adopetme.databinding.ActivityShowcaseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ShowcaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowcaseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowcaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}