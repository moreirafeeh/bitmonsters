package com.example.bitpets

import android.media.MediaPlayer
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bitpets.databinding.ActivityPetRoomBinding

class PetRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPetRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()



        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_pet_room)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        MusicSelectorScreen(R.raw.upresia)
    }

    fun MusicSelectorScreen(music: Int){
        val mp: MediaPlayer = MediaPlayer.create(this, music)
        mp.setOnCompletionListener { mp -> mp.release() }
        mp.start()
    }

}