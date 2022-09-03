package com.example.adopetme.repository

import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.adopetme.model.dog.DogPhoto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class DogPhotoRepositoryImpl: DogPhotoRepository {
    private val firebaseAuth: FirebaseAuth = Firebase.auth
    private val firebaseStore: FirebaseFirestore = Firebase.firestore
    private var storageReference = FirebaseStorage.getInstance().reference
    private val collectionReference: CollectionReference = firebaseStore.collection("users")

    private val liveData: MutableLiveData<MutableList<DogPhoto>?> = MutableLiveData<MutableList<DogPhoto>?>()
    private lateinit var dogPicture: Uri

    override fun save(dogPhoto: DogPhoto) {
        val documentReference: DocumentReference = collectionReference.document(firebaseAuth.currentUser!!.uid)
        val imageReference =
            storageReference.child("images/usersDogs/${firebaseAuth.currentUser!!.uid}/${dogPhoto.picture.lastPathSegment}")
        imageReference.putFile(dogPhoto.picture)
            .addOnSuccessListener {
                Log.d(TAG, "Image uploaded $imageReference")
                imageReference.downloadUrl.addOnSuccessListener {
                    val newPhoto: MutableMap<String, Any> = HashMap()
                    newPhoto["id"] = dogPhoto.id!!.toLong()
                    newPhoto["picture"] = it

                    documentReference.collection("dogimages").document().set(newPhoto).addOnSuccessListener {
                            Log.d(TAG, "onSuccess: Dog photo is created for ${firebaseAuth.currentUser!!.uid}")
                        }
                            .addOnFailureListener{
                                error -> Log.d(TAG, "onFailure: ${error.message}")
                            }
                    }
                    .addOnFailureListener{
                        Log.e(TAG, "OnFailure: ${it.message}")
                    }
            }
        val handle = Handler()
        handle.postDelayed(Runnable {
            run() {
                val updated: MutableList<DogPhoto>? = liveData.value
                updated?.add(dogPhoto)
                if (updated != null) {
                    liveData.postValue(updated)
                }
            }
        }, 2000)
    }

    override fun delete(dogPhoto: DogPhoto) {
        var documentReference: DocumentReference
        collectionReference.document(firebaseAuth.currentUser!!.uid).collection("dogimages").get().addOnSuccessListener {
            photos ->
                for (photo in photos){
                    Log.d(TAG, "Photo id is: ${dogPhoto.id} while in database is: ${photo.data.get("id") as Long?}")
                    if(photo.data.get("id") as Long? == dogPhoto.id){
                        documentReference = collectionReference.document(firebaseAuth.currentUser!!.uid).collection("dogimages").document(photo.id)
                        documentReference.delete()
                        Log.d(TAG, "Dog photo was deleted")
                    }
                }
        }
        val handler = Handler()
        handler.postDelayed(Runnable {
            run() {
                val updated: MutableList<DogPhoto>? = liveData.value
                updated?.remove(dogPhoto)
                if (updated != null) {
                    liveData.postValue(updated)
                }
            }
        }, 2000)
    }


    override fun getAllDogPhotos(): MutableLiveData<MutableList<DogPhoto>?> {
        val handler = Handler()
        handler.postDelayed(Runnable {
            if (firebaseAuth.currentUser?.uid != null) {
                collectionReference.document(firebaseAuth.currentUser!!.uid).collection("dogimages")
                    .get().addOnSuccessListener { snapshosts ->
                        if (snapshosts != null && !snapshosts.isEmpty) {
                            val photos = mutableListOf<DogPhoto>()
                            for (photo in snapshosts) {
                                dogPicture = Uri.parse(photo.data.get("picture") as String)
                                photos.add(
                                    DogPhoto(
                                        dogPicture,
                                        photo.data.get("id") as Long?,
                                    )
                                )
                            }
                            liveData.postValue(photos)
                        } else {
                            Log.d(TAG, "No data")
                        }
                    }
            } else {
                liveData.value?.clear()
            }

        }, 2000)
        return liveData
    }
}