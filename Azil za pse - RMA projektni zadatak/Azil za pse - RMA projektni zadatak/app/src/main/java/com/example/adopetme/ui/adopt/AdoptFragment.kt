package com.example.adopetme.ui.adopt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.adopetme.databinding.AdoptFragmentBinding
import com.example.adopetme.viewmodel.DogViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel


class AdoptFragment: Fragment() {

    private lateinit var binding: AdoptFragmentBinding
    private val viewModel by viewModel<DogViewModel>()
    private val args: AdoptFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdoptFragmentBinding.inflate(layoutInflater)
        binding.backButton.setOnClickListener { backToDisplay() }
        binding.buttonAdopt.setOnClickListener { adopt() }
        viewModel.getDogById(args.dogId)?.let { Picasso.get().load(it.picture).into(binding.dogAdopt) }
        return binding.root
    }

    private fun adopt() {
        Toast.makeText(context, "Yay! ${viewModel.getDogById(args.dogId)?.name} is adopted!", Toast.LENGTH_LONG).show()
        viewModel.getDogById(args.dogId)?.let { viewModel.deleteDog(it) }
        val action = AdoptFragmentDirections.actionAdoptFragmentToDisplayFragment()
        findNavController().navigate(action)
    }

    private fun backToDisplay() {
        val action = AdoptFragmentDirections.actionAdoptFragmentToDisplayFragment()
        findNavController().navigate(action)
    }

}