package com.example.adopetme.ui.login


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.adopetme.ShowcaseActivity
import com.example.adopetme.databinding.LoginFragmentBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment: Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater)
        firebaseAuth = Firebase.auth
        binding.loginButton.setOnClickListener { login() }
        binding.backButton.setOnClickListener { back() }
        return binding.root
    }

    private fun back() {
        val action = LoginFragmentDirections.actionLoginFragmentToWelcomeFragment()
        findNavController().navigate(action)
    }

    //Edit everything around login

    private fun login() {
        val email = binding.emailET.text.toString().trim()
        val password = binding.passwordET.text.toString().trim()

        if(TextUtils.isEmpty(email)){
            binding.emailET.setError("Missing e-mail!")
        }

        if(TextUtils.isEmpty(password)){
            binding.passwordET.setError("Missing password!")
        }

        firebaseAuth.signInWithEmailAndPassword(email,password).
        addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Logged in successfully!", Toast.LENGTH_SHORT).show()
                startActivity(
                    Intent(
                        context,
                        ShowcaseActivity::class.java
                    )
                )
            } else {
                Toast.makeText(
                    context,
                    "Error!" + task.exception?.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}