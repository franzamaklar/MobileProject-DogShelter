package com.example.adopetme.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.adopetme.R
import com.example.adopetme.databinding.SearchResultCellBinding
import com.example.adopetme.model.dog.Dog
import com.example.adopetme.ui.display.OnDogSelectedListener

class SearchDogAdapter : RecyclerView.Adapter<SearchViewHolder>(), Filterable {


    val dogs = mutableListOf<Dog>()
    val allDogs = mutableListOf<Dog>()
    var onDogSelectedListener: OnDogSelectedListener? = null

    fun setDogs(dogs: List<Dog>){
        this.dogs.clear()
        this.dogs.addAll(dogs)
        this.allDogs.addAll(dogs)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder{
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result_cell, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int){
        val dog = dogs[position]
        holder.bind(dog)
        onDogSelectedListener?.let { onDogSelectedListener ->
            holder.itemView.setOnClickListener { onDogSelectedListener.onDogSelected(dog.id)}
        }
    }

    override fun getItemCount(): Int = dogs.count()

    override fun getFilter(): Filter {
        return customFilter
    }

    private val customFilter = object : Filter(){
        override fun performFiltering(p0: CharSequence?): FilterResults {
            var filteredDogs = mutableListOf<Dog>()
            if(p0.toString().isEmpty()){
                filteredDogs.addAll(allDogs)
            }else{
                for(Dog in allDogs){
                    if(Dog.breed.lowercase().contains(p0.toString().lowercase())){
                        filteredDogs.add(Dog)
                    }
                }
            }
            var filterResults = FilterResults()
            filterResults.values = filteredDogs
            return filterResults
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            dogs.clear()
            if (p1 != null) {
                dogs.addAll(p1.values as Collection<Dog>)
            }
            notifyDataSetChanged()
        }

    }

}

class SearchViewHolder(cellView: View): RecyclerView.ViewHolder(cellView){
    fun bind(dog: Dog){
        val binding = SearchResultCellBinding.bind(itemView)
        binding.nameHolder.text = dog.name
        binding.breedHolder.text = dog.breed
        binding.dogImage.setImageResource(dog.picture)
    }
}
