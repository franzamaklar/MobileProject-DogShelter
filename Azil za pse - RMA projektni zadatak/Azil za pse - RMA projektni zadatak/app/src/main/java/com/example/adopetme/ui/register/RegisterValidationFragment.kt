package com.example.adopetme.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.adopetme.ShowcaseActivity
import com.example.adopetme.databinding.RegisterValidationFragmentBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegisterValidationFragment : Fragment() {

    private lateinit var binding: RegisterValidationFragmentBinding
    private val args: RegisterValidationFragmentArgs by navArgs()
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var firebaseStore:FirebaseFirestore
    private lateinit var userID: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = Firebase.auth
        firebaseStore = Firebase.firestore
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
        val credentials: List<String> = output.split(",")
        if(credentials.get(1).equals(binding.passwordET.text.toString())) {
            firebaseAuth.createUserWithEmailAndPassword(credentials.get(0), credentials.get(1))
                .addOnCompleteListener(OnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(context, "User created!", Toast.LENGTH_SHORT).show()
                        userID = firebaseAuth.currentUser!!.uid
                        val documentReference: DocumentReference =
                            firebaseStore.collection("users").document(userID)
                        val user: MutableMap<String, Any> = HashMap()
                        user["username"] = username
                        user["email"] = credentials.get(0)

                        documentReference.set(user).addOnSuccessListener {
                            Log.d(
                                "TAG",
                                "onSuccess: user profile is created for$userID"
                            )
                        }.addOnFailureListener { e -> Log.d("TAG", "onFailure: $e") }
                    }
                })
            Toast.makeText(context, "Sign in successful",  Toast.LENGTH_LONG).show()
            startActivity(
                Intent(
                    context,
                    ShowcaseActivity::class.java
                )
            )
        }else
            Toast.makeText(context, "Password is not compatible",  Toast.LENGTH_LONG).show()
    }
}