package com.example.adopetme.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adopetme.databinding.ActivityShowcaseBinding

class ShowcaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowcaseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowcaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}