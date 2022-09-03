package com.example.adopetme.ui.map

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adopetme.R
import com.example.adopetme.ui.ShowcaseActivity
import com.example.adopetme.databinding.ActivityMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        binding.backButton.setOnClickListener { switchToShowcase() }
    }

    private fun switchToShowcase() {
        startActivity(
            Intent(
                this,
                ShowcaseActivity::class.java
            )
        )
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val shelter = LatLng(45.539547, 18.791300)
        mMap.addMarker(MarkerOptions().position(shelter).title("Marker in Azil za pse - Osijek"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(shelter))
    }
}