package com.example.adopetme.repository

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.adopetme.model.dog.Dog
import com.example.adopetme.model.user.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class UserRepositoryImpl: UserRepository {
    private var firebaseAuth: FirebaseAuth = Firebase.auth
    private var firebaseStore:FirebaseFirestore = Firebase.firestore
    private var storageReference = FirebaseStorage.getInstance().reference
    private var collectionReference: CollectionReference = firebaseStore.collection("users")

    private var liveData: MutableLiveData<MutableList<User>> = MutableLiveData<MutableList<User>>()
    private var dog:MutableLiveData<Dog> = MutableLiveData<Dog>()
    private lateinit var dogPicture: Uri

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

    override fun update(user: User, dog: Dog) {
        var documentReference: DocumentReference
        collectionReference.get().addOnSuccessListener {
                users ->
                    for (usero in users){
                        if(usero.id == user.id){
                            collectionReference.document(usero.id).update("hasDog", true)
                            documentReference = collectionReference.document(usero.id).collection("dog").document()
                            val adoptedDog: MutableMap<String, Any> = HashMap()
                            adoptedDog["id"] = dog.id!!.toLong()
                            adoptedDog["name"] = dog.name
                            adoptedDog["breed"] = dog.breed
                            adoptedDog["gender"] = dog.gender
                            adoptedDog["age"] = dog.age
                            adoptedDog["picture"] = dog.picture
                            documentReference.set(adoptedDog).addOnSuccessListener {
                                Log.d(
                                    TAG,
                                    "onSucess: Usero of id: ${usero.id} as ${usero.data.get("username")} was deleted"
                                )
                            }
                        }
                    }

        }
        val users = liveData.value?.iterator()
        users?.forEach {
            if(it.id == user.id){
                it.hasDog = true
            }
        }
    }

    override fun update(user: User, picture: Uri) {
        val imageReference = storageReference.child("images/usersIcons/${picture.lastPathSegment}")
        imageReference.putFile(picture)
            .addOnSuccessListener {
                Log.d(TAG, "Image uploaded $imageReference")
                imageReference.downloadUrl.addOnSuccessListener {
                    downloadUrl ->
                        collectionReference.get().addOnSuccessListener {
                            users ->
                                for (usero in users){
                                    if(usero.id == user.id){
                                    collectionReference.document(usero.id).update("picture", downloadUrl)
                                        .addOnSuccessListener {
                                            Log.d(TAG, "onSucess: Icon succesfully uploaded")
                                        }
                                        .addOnFailureListener {
                                            Log.d(TAG, "onFailure: ${it.message}")
                                        }

                            }
                        }
                    }
                    val users = liveData.value?.iterator()
                    users?.forEach {
                        if(it.id == user.id){
                            it.picture = downloadUrl.toString()
                        }
                    }
                }
            }
    }

    override fun getUserById(Id: String?): User? {
        val users = liveData.value?.iterator()
        users?.forEach {
            if(it.id == Id){
                return it
            }
        }
        return null
    }

    override fun getAllUsers(): MutableLiveData<MutableList<User>> {
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
            }
        }
        return liveData
    }

    override fun isRegistered(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                task ->
                    task.isSuccessful

            }
    }

    override fun getUserDog(user: User): MutableLiveData<Dog>? {
        collectionReference.document(user.id).collection("dog").get().addOnSuccessListener {
                it.forEach{
                    dogo ->
                        dogPicture = Uri.parse(dogo.data.get("picture") as String)
                        dog.postValue(Dog(
                            dogo.data.get("id") as Long?,
                            dogo.data.get("breed") as String,
                            dogo.data.get("name") as String,
                            dogo.data.get("gender") as String,
                            dogo.data.get("age") as String,
                            dogPicture
                        ))
            }
        }
        return dog
    }
}