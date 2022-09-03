package com.example.adopetme.ui.register

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.adopetme.ui.ShowcaseActivity
import com.example.adopetme.databinding.RegisterValidationFragmentBinding
import com.example.adopetme.model.user.User
import com.example.adopetme.viewmodel.DogViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterValidationFragment : Fragment() {

    private lateinit var binding: RegisterValidationFragmentBinding
    private var firebaseAuth: FirebaseAuth = Firebase.auth
    private val args: RegisterValidationFragmentArgs by navArgs()
    private val handler: Handler = Handler()

    private val viewModel by viewModel<DogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterValidationFragmentBinding.inflate(layoutInflater)
        binding.registerButton.setOnClickListener { signup() }
        binding.backButton.setOnClickListener { back() }
        return binding.root
    }

    private fun back() {
        val action = RegisterValidationFragmentDirections.actionRegisterValidationFragmentToRegisterFragment()
        findNavController().navigate(action)
    }


    private fun signup() {
        val output = args.credentials
        val username = binding.usernameET.text.toString()
        val validationPassword = binding.passwordET.text.toString()
        val credentials: List<String> = output.split(",")

        if (TextUtils.isEmpty(username)) {
            binding.usernameET.setError("Missing username!")
        } else if (TextUtils.isEmpty(validationPassword)) {
            binding.passwordET.setError("Missing password!")
        } else {
            if (credentials.get(1).equals(validationPassword)) {
                viewModel.saveUser(
                    User(
                        "",
                        username,
                        credentials.get(0),
                        credentials.get(1),
                        "https://firebasestorage.googleapis.com/v0/b/adopet-me-project.appspot.com/o/images%2FusersIcons%2F2131165306?alt=media&token=3083b02e-9154-4464-aff3-d2e7c4bc44da",
                        false,
                        false
                    )
                )
                handler.postDelayed(Runnable
                {
                    run() {
                        if (firebaseAuth.currentUser != null) {
                            Toast.makeText(context, "Sign in successful", Toast.LENGTH_LONG).show()
                            startActivity(
                                Intent(
                                    context,
                                    ShowcaseActivity::class.java
                                )
                            )
                        } else {
                            Toast.makeText(context, "Unsuccessful sign in", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }, 2000
                )

            } else {
                Toast.makeText(context, "Password is not compatible", Toast.LENGTH_LONG).show()
            }
        }

    }
}