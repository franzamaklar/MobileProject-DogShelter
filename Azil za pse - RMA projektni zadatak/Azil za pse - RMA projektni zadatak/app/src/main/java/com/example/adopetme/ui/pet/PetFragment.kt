package com.example.adopetme.ui.pet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.adopetme.databinding.PetFragmentBinding
import com.example.adopetme.viewmodel.DogViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel


class PetFragment: Fragment() {
    private lateinit var binding:PetFragmentBinding
    private val viewModel by viewModel<DogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PetFragmentBinding.inflate(layoutInflater)
        binding.backButton.setOnClickListener { backToDisplay() }
        showcasePet()
        return binding.root
    }

    private fun showcasePet() {
        viewModel.getUserById()?.let {
            user ->
                viewModel.getUserDog(user)?.observe(viewLifecycleOwner){
                    dog ->
                    binding.breedName.text = dog?.breed
                    binding.dogName.text = dog?.name
                    binding.dogAge.text = dog?.age
                    binding.dogGender.text = dog?.gender
                    Picasso.get().load(dog?.picture).into(binding.yourDogImage)
            }
        }

    }

    private fun backToDisplay() {
        val action = PetFragmentDirections.actionPetFragmentToDisplayFragment()
        findNavController().navigate(action)
    }
}