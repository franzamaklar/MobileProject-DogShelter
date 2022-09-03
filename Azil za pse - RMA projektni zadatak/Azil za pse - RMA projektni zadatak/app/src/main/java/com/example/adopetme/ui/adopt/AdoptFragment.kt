package com.example.adopetme.ui.adopt

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.adopetme.R
import com.example.adopetme.databinding.AdoptFragmentBinding
import com.example.adopetme.viewmodel.DogViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel


class AdoptFragment: Fragment() {

    private lateinit var binding: AdoptFragmentBinding
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var builder:Notification.Builder

    private val viewModel by viewModel<DogViewModel>()
    private val args: AdoptFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdoptFragmentBinding.inflate(layoutInflater)
        binding.backButton.setOnClickListener { backToDisplay() }
        if(viewModel.getUserById()!!.hasDog || viewModel.getUserById()!!.isAdmin){
            binding.buttonAdopt.visibility = View.INVISIBLE
        }else {
            binding.buttonAdopt.setOnClickListener { adopt() }
        }
        viewModel.getDogById(args.dogId)?.let { Picasso.get().load(it.picture).into(binding.dogAdopt) }
        return binding.root
    }

    private fun adopt() {
        notificationManager = activity?.getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationIntent = Intent()
        val pendingIntent = PendingIntent.getActivity(activity, 0, notificationIntent, 0)
        notificationChannel = NotificationChannel("i.apps.notifications", "Dog adopted notification", NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel.enableVibration(true)
        notificationManager.createNotificationChannel(notificationChannel)

        builder = Notification.Builder(context, "i.apps.notifications")
            .setSmallIcon(R.mipmap.ic_dog)
            .setContentTitle("Dog adopted!")
            .setContentIntent(pendingIntent)
        notificationManager.notify(1, builder.build())

        Toast.makeText(context, "Yay! ${viewModel.getDogById(args.dogId)?.name} is adopted!", Toast.LENGTH_LONG).show()
        viewModel.getDogById(args.dogId)?.let { viewModel.deleteDog(it) }
        viewModel.getUserById()?.let {
            user ->
                viewModel.getDogById(args.dogId)
                    ?.let {
                            dog -> viewModel.updateUser(user, dog)
                    }
        }
        val action = AdoptFragmentDirections.actionAdoptFragmentToDisplayFragment()
        findNavController().navigate(action)
    }

    private fun backToDisplay() {
        val action = AdoptFragmentDirections.actionAdoptFragmentToDisplayFragment()
        findNavController().navigate(action)
    }

}