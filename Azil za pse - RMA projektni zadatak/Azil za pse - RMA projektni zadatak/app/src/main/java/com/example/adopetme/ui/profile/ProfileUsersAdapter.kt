package com.example.adopetme.ui.profile

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adopetme.R
import com.example.adopetme.databinding.SearchResultCellBinding
import com.example.adopetme.model.user.User
import com.squareup.picasso.Picasso

class ProfileUsersAdapter: RecyclerView.Adapter<UserViewHolder>(){
    val users = mutableListOf<User>()

    fun setUsers(useros: List<User>){
        users.clear()
        users.addAll(useros)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result_cell, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = users.count()

}

class UserViewHolder(cellView: View): RecyclerView.ViewHolder(cellView){
    fun bind(user: User){
        val binding = SearchResultCellBinding.bind(itemView)
        binding.nameHolder.text = user.username
        binding.breedHolder.text = user.email
        val handler = Handler()
        handler.postDelayed(Runnable
        {
            run(){
                Picasso.get().load(user.picture).into(binding.dogImage)
            }

        }, 1000)

    }
}