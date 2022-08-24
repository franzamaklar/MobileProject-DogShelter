package com.example.adopetme.ui.adopt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.adopetme.data.DogRepository
import com.example.adopetme.databinding.AdoptFragmentBinding
import com.example.adopetme.di.DogRepositoryFactory


class AdoptFragment: Fragment() {

    private lateinit var binding: AdoptFragmentBinding
    private var dogRepository: DogRepository = DogRepositoryFactory.dogRepository
    private val args: AdoptFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdoptFragmentBinding.inflate(layoutInflater)
        binding.backButton.setOnClickListener { backToDisplay() }
        binding.buttonAdopt.setOnClickListener { adopt() }
        dogRepository.getDogById(args.dogId)?.let { binding.dogAdopt.setImageResource(it.picture) }
        return binding.root
    }

    private fun adopt() {
        Toast.makeText(context, "Yay! ${dogRepository.getDogById(args.dogId)?.name} is adopted!", Toast.LENGTH_LONG).show()
        dogRepository.getDogById(args.dogId)?.let { dogRepository.delete(it) }
        val action = AdoptFragmentDirections.actionAdoptFragmentToDisplayFragment()
        findNavController().navigate(action)
    }

    private fun backToDisplay() {
        val action = AdoptFragmentDirections.actionAdoptFragmentToDisplayFragment()
        findNavController().navigate(action)
    }

}