package com.example.adopetme.ui.display

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adopetme.R
import com.example.adopetme.databinding.DogCellBinding
import com.example.adopetme.model.dog.Dog
import com.squareup.picasso.Picasso

class DisplayDogAdapter : RecyclerView.Adapter<DogViewHolder>() {

    val dogs = mutableListOf<Dog>()
    var onDogSelectedListener: OnDogSelectedListener? = null

    fun setDogs(dogs: List<Dog>){
        this.dogs.clear()
        this.dogs.addAll(dogs.sortedBy { dog -> dog.id })
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder{
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dog_cell, parent, false)
        return DogViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int){
        val dog = dogs[position]
        holder.bind(dog)
        onDogSelectedListener?.let { onDogSelectedListener ->
            holder.itemView.setOnClickListener { onDogSelectedListener.onDogSelected(dog.id)}
        }
    }

    override fun getItemCount(): Int = dogs.count()

}

class DogViewHolder(cellView: View): RecyclerView.ViewHolder(cellView){
    fun bind(dog: Dog){
        val binding = DogCellBinding.bind(itemView)
        binding.dogGender.text = dog.gender
        binding.dogName.text = dog.name
        binding.dogAge.text = dog.age
        binding.breedName.text = dog.breed
        Picasso.get().load(dog.picture).into(binding.dogPicture)

    }
}