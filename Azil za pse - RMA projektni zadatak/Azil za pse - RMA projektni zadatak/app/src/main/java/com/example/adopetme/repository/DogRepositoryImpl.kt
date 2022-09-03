package com.example.adopetme.repository

import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.adopetme.model.dog.Dog
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class DogRepositoryImpl(): DogRepository {

    private val firebaseStore: FirebaseFirestore = Firebase.firestore
    private val collectionReference: CollectionReference = firebaseStore.collection("dogs")
    private var storageReference = FirebaseStorage.getInstance().reference

    private val liveData: MutableLiveData<MutableList<Dog>?> = MutableLiveData<MutableList<Dog>?>()
    private lateinit var dogPicture: Uri

    override fun save(dog: Dog) {
        val documentReference: DocumentReference = collectionReference.document()
        val imageReference =
            storageReference.child("images/adoptingDogs/${dog.picture.lastPathSegment}")
            imageReference.putFile(dog.picture)
            .addOnSuccessListener {
                Log.d(TAG, "Image uploaded $imageReference")
                imageReference.downloadUrl.addOnSuccessListener {

                    val newDog: MutableMap<String, Any> = HashMap()
                    newDog["id"] = dog.id!!.toLong()
                    newDog["name"] = dog.name
                    newDog["breed"] = dog.breed
                    newDog["gender"] = dog.gender
                    newDog["age"] = dog.age
                    newDog["picture"] = it

                    documentReference.set(newDog).addOnSuccessListener {
                        Log.d(
                            TAG,
                            "onSuccess: Dog is created for ${dog.id}"
                        )
                    }.addOnFailureListener { error ->
                        Log.d(TAG, "onFailure: ${error.message}")
                    }
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "${it.message}")
            }
        val handler = Handler()
        handler.postDelayed(Runnable {
            run() {
                val updated: MutableList<Dog>? = liveData.value
                updated?.add(dog)
                if (updated != null) {
                    liveData.postValue(updated)
                }
            }
        }, 2000)
    }

    override fun delete(dog: Dog) {
        var documentReference: DocumentReference
        collectionReference.get().addOnSuccessListener {
            dogs ->
                for (dogo in dogs){
                    if(dogo.data.get("id") as Long? == dog.id){
                        documentReference = collectionReference.document(dogo.id)
                        documentReference.delete()
                        Log.d(TAG, "onSuccess: Dogo of id: ${dogo.data.get("id")} as ${dogo.data.get("name")} was deleted")
                    }
                }
        }
        val handler = Handler()
        handler.postDelayed(Runnable {
            run() {
                val updated: MutableList<Dog>? = liveData.value
                updated?.remove(dog)
                if (updated != null) {
                    liveData.postValue(updated)
                }
            }
        }, 2000)
    }

    override fun getDogById(id: Long?): Dog? {
        val dogs = liveData.value?.iterator()
        dogs?.forEach {
            if(it.id == id){
                return it
            }
        }
        return null
    }

    override fun getAllDogs(): MutableLiveData<MutableList<Dog>?> {
        collectionReference.get().addOnSuccessListener { snapshots ->

            if (snapshots!= null && !snapshots.isEmpty) {
                val dogs = mutableListOf<Dog>()

                for (dog in snapshots) {
                        dogPicture = Uri.parse(dog.data.get("picture") as String)
                        dogs.add(Dog(
                            dog.data.get("id") as Long?,
                            dog.data.get("breed") as String,
                            dog.data.get("name") as String,
                            dog.data.get("gender") as String,
                            dog.data.get("age") as String,
                            dogPicture
                        ))
                }
                liveData.postValue(dogs)
            } else {
                Log.d(TAG, "No data")

            }
        }
        return liveData
    }

}