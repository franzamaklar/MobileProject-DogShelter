package com.example.adopetme.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adopetme.WelcomeActivity
import com.example.adopetme.databinding.ProfileFragmentBinding
import com.example.adopetme.di.DogRepositoryFactory
import com.example.adopetme.model.dog.Dog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment: Fragment() {
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var verticalAdapter: VerticalAdapter
    private lateinit var horizontalAdapter: ProfileDogAdapter
    private var iterator = 0

    private val adapters = mutableListOf<ProfileDogAdapter>()
    private val dogRepository = DogRepositoryFactory.dogRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = Firebase.auth
        binding = ProfileFragmentBinding.inflate(layoutInflater)
        setUpRecyclerView()
        binding.backButton.setOnClickListener { backToDisplay() }
        binding.logoutButton.setOnClickListener { logout() }
        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.dogsRecylcer.layoutManager = LinearLayoutManager(
            context,
            LinearLayout.VERTICAL,
            false
        )
        horizontalAdapter = ProfileDogAdapter()
        val dogs = dogRepository.getAllDogs()
        val pairOfDogs = mutableListOf<Dog>()
        while(iterator < dogs.size){
            pairOfDogs.add(dogs[iterator])
            if(iterator == 1){
                horizontalAdapter.setDogs(pairOfDogs)
                adapters.add((horizontalAdapter))
                horizontalAdapter = ProfileDogAdapter()
                pairOfDogs.clear()
            }
            iterator++
        }
        horizontalAdapter.setDogs(pairOfDogs)
        adapters.add(horizontalAdapter)
        verticalAdapter = VerticalAdapter()
        verticalAdapter.setAdapters(adapters)
        binding.dogsRecylcer.adapter = verticalAdapter
    }

    private fun backToDisplay() {
        val action = ProfileFragmentDirections.actionProfileFragmentToDisplayFragment()
        findNavController().navigate(action)
    }

    private fun logout() {
        firebaseAuth.signOut()
        startActivity(
            Intent(
                context,
                WelcomeActivity::class.java
            )
        )
    }
}