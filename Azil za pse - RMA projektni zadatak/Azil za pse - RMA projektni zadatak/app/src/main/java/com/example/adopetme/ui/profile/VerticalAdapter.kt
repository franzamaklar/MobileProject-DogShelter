package com.example.adopetme.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adopetme.R
import com.example.adopetme.databinding.UploadsContainerBinding

class VerticalAdapter : RecyclerView.Adapter<VerticalViewHolder>() {

    val adapters = mutableListOf<ProfileDogAdapter>()

    fun setAdapters(adapters: List<ProfileDogAdapter>){
        this.adapters.clear()
        this.adapters.addAll(adapters)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.uploads_container, parent, false)
        return VerticalViewHolder(view)
    }

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        val adapter = adapters[position]
        holder.bind(adapter)
    }

    override fun getItemCount(): Int = adapters.count()

}

class VerticalViewHolder(cellView:View): RecyclerView.ViewHolder(cellView){
    fun bind(adapter: ProfileDogAdapter){
        val binding = UploadsContainerBinding.bind(itemView)
        binding.container.layoutManager = LinearLayoutManager(
            binding.container.context,
            LinearLayout.HORIZONTAL,
            false
        )
        binding.container.adapter = adapter
    }

}