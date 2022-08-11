package com.example.adopetme.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.adopetme.databinding.RegisterFragmentBinding


class RegisterFragment : Fragment() {
    private lateinit var binding: RegisterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegisterFragmentBinding.inflate(layoutInflater)
        binding.registerButton.setOnClickListener { moveToValidate() }
        binding.backButton.setOnClickListener { back() }
        return binding.root
    }

    private fun back() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToWelcomeFragment()
        findNavController().navigate(action)
    }

    private fun moveToValidate() {
        val email = binding.emailET.text.toString()
        val password = binding.passwordET.text.toString()
        val credentials = "$email,$password"
        val action = RegisterFragmentDirections.actionRegisterFragmentToRegisterValidationFragment(credentials)
        findNavController().navigate(action)
    }
}