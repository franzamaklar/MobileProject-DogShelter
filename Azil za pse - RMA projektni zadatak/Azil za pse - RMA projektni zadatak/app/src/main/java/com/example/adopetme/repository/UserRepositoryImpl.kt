package com.example.adopetme.repository

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.adopetme.model.user.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates

class UserRepositoryImpl: UserRepository {
    private var firebaseAuth: FirebaseAuth = Firebase.auth
    private var firebaseStore:FirebaseFirestore = Firebase.firestore
    private var collectionReference: CollectionReference = firebaseStore.collection("users")

    private var liveData: MutableLiveData<List<User>> = MutableLiveData<List<User>>()

    override fun save(user: User)  {
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {
                task ->
                    if (task.isSuccessful){
                        user.id = firebaseAuth.currentUser!!.uid
                        val documentReference: DocumentReference = collectionReference.document(user.id)
                        val newUser: MutableMap<String, Any> = HashMap()
                        newUser["username"] = user.username
                        newUser["email"] = user.email
                        newUser["password"] = user.password
                        newUser["admin"] = user.isAdmin
                        newUser["hasDog"] = user.hasDog
                        newUser["picture"] = user.picture

                        documentReference.set(user).addOnSuccessListener {
                            Log.d(TAG, "OnSuccess: user profile created for ${user.email}")
                            return@addOnSuccessListener
                        }
                            .addOnFailureListener{
                                Log.d(TAG, "OnFailure: ${it.message}")
                            return@addOnFailureListener
                            }
                    }
            }
    }

    override fun delete(user: User) {
        var documentReference: DocumentReference
        collectionReference.get().addOnSuccessListener {
                users ->
                    for (usero in users){
                        if(usero.id == user.id){
                            documentReference = collectionReference.document(usero.id)
                            documentReference.delete()
                            Log.d(TAG, "Usero of id: ${usero.id} as ${usero.data.get("username")} was deleted")
                            return@addOnSuccessListener
                        }
                    }
        }
    }

    override fun getAllUsers(): MutableLiveData<List<User>> {
        collectionReference.get().addOnSuccessListener { snapshots ->
            if(snapshots != null && !snapshots.isEmpty){
                val users = mutableListOf<User>()
                for(user in snapshots){
                    users.add(User(
                        user.data.get("id") as String,
                        user.data.get("username") as String,
                        user.data.get("email") as String,
                        user.data.get("password") as String,
                        user.data.get("picture") as String,
                        user.data.get("admin") as Boolean,
                        user.data.get("hasDog") as Boolean,
                    ))
                }
                liveData.postValue(users)
            }else{
                Log.d(TAG, "No data")
                return@addOnSuccessListener
            }
        }
        return liveData
    }

    override fun isRegistered(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                task ->
                    task.isSuccessful
                    return@addOnCompleteListener

            }
    }
}