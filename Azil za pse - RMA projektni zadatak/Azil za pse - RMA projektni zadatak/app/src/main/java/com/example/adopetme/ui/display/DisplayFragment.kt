package com.example.adopetme.ui.display

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adopetme.databinding.DisplayFragmentBinding
import com.example.adopetme.model.dog.Dog
import com.example.adopetme.ui.map.MapsActivity
import com.example.adopetme.viewmodel.DogViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel


class DisplayFragment : Fragment(), OnDogSelectedListener {
    private lateinit var binding: DisplayFragmentBinding
    private lateinit var  adapter: DisplayDogAdapter
    private val firebaseAuth: FirebaseAuth = Firebase.auth
    private val viewModel by viewModel<DogViewModel>()
    private val handler:Handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DisplayFragmentBinding.inflate(layoutInflater)
        viewModel.dogs.observe(viewLifecycleOwner){
            if(it != null && it.isNotEmpty()){
                adapter.setDogs(it)
            }
        }
        setUpRecyclerView()

        binding.iconAdd.visibility = View.INVISIBLE
        binding.iconPet.visibility = View.INVISIBLE
        binding.borderHolder.visibility = View.INVISIBLE


        binding.iconSearch.setOnClickListener { switchToSearch() }
        binding.iconMap.setOnClickListener { switchToMaps() }
        binding.iconProfile.setOnClickListener { switchToProfile() }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        checkButtons()
    }

    private fun checkButtons() {
        handler.postDelayed(Runnable {
            run() {
                viewModel.users.observe(viewLifecycleOwner){
                    it.forEach{
                        if(it.id == firebaseAuth.currentUser!!.uid){
                            if(it.hasDog == true){
                                binding.iconPet.visibility = View.VISIBLE
                                binding.borderHolder.visibility = View.VISIBLE
                                binding.iconPet.setOnClickListener{ switchToPet() }
                            }else if(it.isAdmin == true){
                                binding.iconAdd.visibility = View.VISIBLE
                                binding.borderHolder.visibility = View.VISIBLE
                                binding.iconAdd.setOnClickListener { switchToInput() }
                            }
                        }
                    }
                }
            }
        }, 1000)
    }

    private fun switchToPet() {
        val action = DisplayFragmentDirections.actionDisplayFragmentToPetFragment()
        findNavController().navigate(action)
    }

    private fun switchToInput() {
        val action = DisplayFragmentDirections.actionDisplayFragmentToInputFragment()
        findNavController().navigate(action)
    }

    private fun switchToSearch() {
        val action = DisplayFragmentDirections.actionDisplayFragmentToSearchFragment()
        findNavController().navigate(action)
    }

    private fun switchToProfile() {
       val action = DisplayFragmentDirections.actionDisplayFragmentToProfileFragment()
        findNavController().navigate(action)
    }

    private fun switchToMaps() {
        startActivity(
            Intent(
                context,
                MapsActivity::class.java
            )
        )
    }

    private fun setUpRecyclerView() {
        binding.dogRecycler.layoutManager = LinearLayoutManager(
            context,
            LinearLayout.HORIZONTAL,
            false
        )
        adapter = DisplayDogAdapter()
        adapter.onDogSelectedListener = this
        binding.dogRecycler.adapter = adapter
    }


    override fun onDogSelected(dog:Dog) {
        val action = DisplayFragmentDirections.actionDisplayFragmentToAdoptFragment(dog.id ?: -1)
        findNavController().navigate(action)
    }
}