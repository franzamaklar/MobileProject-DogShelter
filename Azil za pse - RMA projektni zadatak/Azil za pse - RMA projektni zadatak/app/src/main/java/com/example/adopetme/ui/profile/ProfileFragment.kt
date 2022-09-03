package com.example.adopetme.ui.profile

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adopetme.ui.WelcomeActivity
import com.example.adopetme.databinding.ProfileFragmentBinding
import com.example.adopetme.model.dog.DogPhoto
import com.example.adopetme.viewmodel.DogViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment: Fragment(), OnUploadSelectedListener {
    private lateinit var binding: ProfileFragmentBinding
    private var firebaseAuth: FirebaseAuth =  Firebase.auth


    private val viewModel by viewModel<DogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ProfileFragmentBinding.inflate(layoutInflater)
        if(viewModel.getUserById()?.isAdmin == false) {
            setUpUploadsRecyclerView()
            if (viewModel.getUserById()?.hasDog == true && viewModel.getUserById()?.isAdmin == false
            ) {
                binding.uploadButton.setOnClickListener { uploadPicture() }
            } else {
                binding.uploadButton.setOnClickListener {
                    Toast.makeText(context, "You have not adopted a dog yet", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }else{
            setUpUsersRecyclerView()
        }

        binding.usernameHolder.text =
            viewModel.getUserById()?.username

        val handler = Handler()
        handler.postDelayed(Runnable
        {
            run(){
                Picasso.get().load(viewModel.getUserById()?.picture).into(binding.iconProfile)
            }
        },2000)

        binding.imageHolder.setOnClickListener { uploadIcon() }
        binding.backButton.setOnClickListener { backToDisplay() }
        binding.logoutButton.setOnClickListener { logout() }
        return binding.root
    }

    private fun uploadIcon() {
        getIconPicture.launch("image/*")
    }

    private val getIconPicture =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ){
            picture ->
                if(picture != null){
                binding.iconProfile.setImageURI(picture)
                viewModel.getUserById()
                    ?.let{ user -> viewModel.updateUser(user, picture) }
            }
        }


    private fun uploadPicture() {
        getDogPicture.launch("image/*")
    }

    private val getDogPicture =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ){
            if(it != null){
                viewModel.saveDogPhoto(
                    DogPhoto(it,(0..1000).random().toLong())
                )
            }
        }

    private fun setUpUsersRecyclerView() {
        binding.dogsRecylcer.layoutManager = LinearLayoutManager(
            context,
            LinearLayout.VERTICAL,
            false
        )
        val userAdapter = ProfileUsersAdapter()
        viewModel.users.observe(viewLifecycleOwner){
            if(it != null){
                Log.d(TAG, "Users size:${it.size}")
                userAdapter.setUsers(it)
            }
        }
        binding.dogsRecylcer.adapter = userAdapter

    }

    private fun setUpUploadsRecyclerView() {
        binding.dogsRecylcer.layoutManager = LinearLayoutManager(
            context,
            LinearLayout.HORIZONTAL,
            false
        )
        val dogAdapter = ProfileDogAdapter()
        viewModel.dogPhotos.observe(viewLifecycleOwner) {
            if (it != null) {
                dogAdapter.setDogs(it)
            }

        }
        dogAdapter.onUploadSelectedListener = this
        binding.dogsRecylcer.adapter = dogAdapter
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