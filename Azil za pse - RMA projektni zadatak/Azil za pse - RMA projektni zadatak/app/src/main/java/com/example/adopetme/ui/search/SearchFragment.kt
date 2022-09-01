package com.example.adopetme.ui.search

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adopetme.databinding.SearchFragmentBinding
import com.example.adopetme.viewmodel.DogViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {

    private lateinit var binding: SearchFragmentBinding
    private lateinit var adapter:SearchDogAdapter
    private val viewModel by viewModel<DogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(layoutInflater)
        setUpRecycler()
        setUpSearchable()
        binding.backButton.setOnClickListener { backToDisplay() }
        return binding.root
    }

    private fun setUpSearchable() {
        binding.searchEngine.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return false
                }
            })
    }

    private fun setUpRecycler() {
        binding.queryResultsRecylcer.layoutManager = LinearLayoutManager(
            context,
            LinearLayout.VERTICAL,
            false
        )
        adapter = SearchDogAdapter()
        viewModel.dogs.observe(viewLifecycleOwner){
            Log.d(TAG, "Size value: ${it.size}")
            adapter.setDogs(it)
        }
        binding.queryResultsRecylcer.adapter = adapter
    }

    private fun backToDisplay() {
        val action = SearchFragmentDirections.actionSearchFragmentToDisplayFragment()
        findNavController().navigate(action)
    }



}