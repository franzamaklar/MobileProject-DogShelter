package com.example.adopetme.ui.profile


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adopetme.R
import com.example.adopetme.databinding.UploadCellBinding
import com.example.adopetme.model.dog.DogPhoto
import com.squareup.picasso.Picasso


class ProfileDogAdapter : RecyclerView.Adapter<DogViewHolder>() {

    val dogsPhotos = mutableListOf<DogPhoto>()
    var onUploadSelectedListener: OnUploadSelectedListener? = null

    fun setDogs(dogosPhotos: List<DogPhoto>){
        dogsPhotos.clear()
        dogsPhotos.addAll(dogosPhotos.sortedBy { dog -> dog.id })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.upload_cell, parent, false)
        return DogViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val dogphoto = dogsPhotos[position]
        holder.bind(dogphoto)
        onUploadSelectedListener?.let { onUploadSelectedListener ->
            holder.itemView.setOnLongClickListener {
                onUploadSelectedListener.OnUploadSelected(dogphoto)
                true
            }
        }
    }

    override fun getItemCount(): Int = dogsPhotos.count()
}

class DogViewHolder(cellView: View): RecyclerView.ViewHolder(cellView){
    fun bind(dogPhoto: DogPhoto){
        val binding = UploadCellBinding.bind(itemView)
        Picasso.get().load(dogPhoto.picture).into(binding.dogPicture)
    }
}