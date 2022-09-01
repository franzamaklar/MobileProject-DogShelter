package com.example.adopetme.ui.profile

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adopetme.ui.WelcomeActivity
import com.example.adopetme.databinding.ProfileFragmentBinding
import com.example.adopetme.model.dog.Dog
import com.example.adopetme.model.dog.DogPhoto
import com.example.adopetme.viewmodel.DogViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment: Fragment(), OnUploadSelectedListener {
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var adapter: ProfileDogAdapter

    private val viewModel by viewModel<DogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = Firebase.auth
        binding = ProfileFragmentBinding.inflate(layoutInflater)
        setUpRecyclerView()
        binding.uploadButton.setOnClickListener { uploadPicture() }
        binding.backButton.setOnClickListener { backToDisplay() }
        binding.logoutButton.setOnClickListener { logout() }
        return binding.root
    }

    private fun uploadPicture() {
        getResult.launch("image/*")
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ){
            if(it != null){
                viewModel.saveDogPhoto(
                    DogPhoto(it,0)
                )
            }
        }

    private fun setUpRecyclerView() {
        binding.dogsRecylcer.layoutManager = LinearLayoutManager(
            context,
            LinearLayout.HORIZONTAL,
            false
        )
        adapter = ProfileDogAdapter()
        adapter.onUploadSelectedListener = this
        binding.dogsRecylcer.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    private fun updateData() {
        viewModel.dogPhotos.observe(viewLifecycleOwner){
            adapter.setDogs(it)

        }
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

    override fun OnUploadSelected(dogPhoto: DogPhoto) {
        viewModel.deleteDogPhoto(dogPhoto)
    }
}