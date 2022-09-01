package com.example.adopetme.ui.login


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.adopetme.ui.ShowcaseActivity
import com.example.adopetme.databinding.LoginFragmentBinding
import com.example.adopetme.viewmodel.DogViewModel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment: Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private var firebaseAuth: FirebaseAuth = Firebase.auth
    private var handler: Handler = Handler()
    private val viewModel by viewModel<DogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater)
        binding.loginButton.setOnClickListener { login() }
        binding.backButton.setOnClickListener { back() }
        return binding.root
    }

    private fun back() {
        val action = LoginFragmentDirections.actionLoginFragmentToWelcomeFragment()
        findNavController().navigate(action)
    }


    private fun login() {
        val email = binding.emailET.text.toString().trim()
        val password = binding.passwordET.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            binding.emailET.setError("Missing e-mail!")
        } else if (TextUtils.isEmpty(password)) {
            binding.passwordET.setError("Missing password!")
        } else {
            viewModel.isUserRegistered(email, password)
            handler.postDelayed(Runnable {
                run(){
                    if(firebaseAuth.currentUser != null){
                        Toast.makeText(context, "Logged in successfully!", Toast.LENGTH_SHORT).show()
                        startActivity(
                            Intent(
                                context,
                                ShowcaseActivity::class.java
                            )
                        )
                    }else
                        Toast.makeText(context, "Error! Login unsuccessful!", Toast.LENGTH_LONG).show()
                }
            }, 2000)
        }
    }
}