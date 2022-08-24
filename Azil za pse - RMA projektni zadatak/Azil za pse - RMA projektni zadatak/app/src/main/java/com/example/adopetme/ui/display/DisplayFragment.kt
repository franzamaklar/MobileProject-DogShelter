package com.example.adopetme.ui.display

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adopetme.databinding.DisplayFragmentBinding
import com.example.adopetme.di.DogRepositoryFactory
import com.example.adopetme.ui.map.MapsActivity


class DisplayFragment : Fragment(), OnDogSelectedListener {
    private lateinit var binding: DisplayFragmentBinding
    private lateinit var  adapter: DisplayDogAdapter
    private val dogRepository = DogRepositoryFactory.dogRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DisplayFragmentBinding.inflate(layoutInflater)
        setUpRecyclerView()
        binding.iconAdd.setOnClickListener { switchToInput() }
        binding.iconSearch.setOnClickListener { switchToSearch() }
        binding.iconMap.setOnClickListener { switchToMaps() }
        binding.iconProfile.setOnClickListener { switchToProfile() }
        return binding.root
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

    override fun onResume() {
        super.onResume()
        updateData()
    }

    private fun updateData() {
        adapter.setDogs(dogRepository.getAllDogs())
    }


    override fun onDogSelected(id: Long?) {
        val action = DisplayFragmentDirections.actionDisplayFragmentToAdoptFragment(id ?: -1)
        findNavController().navigate(action)
    }
}