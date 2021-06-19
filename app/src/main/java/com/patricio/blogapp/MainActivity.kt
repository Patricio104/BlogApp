package com.patricio.blogapp

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.patricio.blogapp.core.hide
import com.patricio.blogapp.core.show
import com.patricio.blogapp.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream
import java.lang.RuntimeException
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        observerDestinationChange()
    }

    private fun observerDestinationChange(){
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.loginFragment ->{
                    binding.bottomNavigationView.hide()
                }

                R.id.registerFragment ->{
                    binding.bottomNavigationView.hide()
                }

                R.id.setupProfileFragment -> {
                    binding.bottomNavigationView.hide()
                }

                else ->{
                    binding.bottomNavigationView.show()
                }
            }
        }
    }
}
