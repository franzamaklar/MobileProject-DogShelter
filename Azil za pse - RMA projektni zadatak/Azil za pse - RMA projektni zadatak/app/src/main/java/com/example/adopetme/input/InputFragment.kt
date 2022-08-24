package com.example.adopetme.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.adopetme.databinding.InputFragmentBinding

class InputFragment: Fragment() {

    private lateinit var binding: InputFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InputFragmentBinding.inflate(layoutInflater)
        binding.backButton.setOnClickListener { backToDisplay() }
        return binding.root
    }

    private fun backToDisplay() {
        val action = InputFragmentDirections.actionInputFragmentToDisplayFragment()
        findNavController().navigate(action)
    }

}