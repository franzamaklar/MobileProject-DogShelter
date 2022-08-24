package com.example.adopetme.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adopetme.R
import com.example.adopetme.databinding.UploadCellBinding
import com.example.adopetme.model.dog.Dog



class ProfileDogAdapter : RecyclerView.Adapter<DogViewHolder>() {

    val dogs = mutableListOf<Dog>()

    fun setDogs(dog: List<Dog>){
        this.dogs.clear()
        this.dogs.addAll(dog)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.upload_cell, parent, false)
        return DogViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val dog = dogs[position]
        holder.bind(dog)
    }

    override fun getItemCount(): Int = dogs.count()
}

class DogViewHolder(cellView: View): RecyclerView.ViewHolder(cellView){
    fun bind(dog: Dog){
        val binding = UploadCellBinding.bind(itemView)
        binding.dogPicture.setImageResource(dog.picture)
    }
}