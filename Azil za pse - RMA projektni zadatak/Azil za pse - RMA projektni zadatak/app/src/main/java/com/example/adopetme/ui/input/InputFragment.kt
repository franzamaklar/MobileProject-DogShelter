package com.example.adopetme.ui.input

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.adopetme.databinding.InputFragmentBinding
import com.example.adopetme.model.dog.Dog
import com.example.adopetme.viewmodel.DogViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel



class InputFragment: Fragment() {

    private lateinit var binding: InputFragmentBinding
    private val viewModel by viewModel<DogViewModel>()
    private lateinit var uploadUri:Uri


    private val dogImage: ImageView by lazy {
        binding.imageHolder
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InputFragmentBinding.inflate(layoutInflater)
        addingNewDog()
        binding.backButton.setOnClickListener { backToDisplay() }

        return binding.root
    }

    private fun addingNewDog() {
        binding.imageHolder.setOnClickListener{
            accessGallery()
        }

        binding.addButton.setOnClickListener {
            val id: Long? = (0..1000).random().toLong()
            val breedName = binding.breedHolder.text.toString()
            val dogName = binding.nameHolder.text.toString()
            val dogAge = binding.ageHolder.text.toString()
            val dogGender:String

            if(binding.genderFemale.isChecked){
                 dogGender = binding.genderFemale.text.toString()
            }else {
                dogGender = binding.genderMale.text.toString()
            }
            viewModel.saveDog(Dog(
                id,
                breedName,
                dogName,
                dogGender,
                dogAge,
                uploadUri
            ))
            backToDisplay()
        }
    }


    private fun accessGallery() {
        getResult.launch("image/*")
    }


    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ){
            if (it != null) {
                uploadUri = it
            }
            dogImage.setImageURI(it)
        }

    private fun backToDisplay() {
        val action = InputFragmentDirections.actionInputFragmentToDisplayFragment()
        findNavController().navigate(action)
    }

}