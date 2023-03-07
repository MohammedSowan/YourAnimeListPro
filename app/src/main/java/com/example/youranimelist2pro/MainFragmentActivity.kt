package com.example.youranimelist2pro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.youranimelist.R
import com.example.youranimelist.databinding.ActivityMainFragmentBinding

import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragmentActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        //val navController = findNavController(R.id.mainFragment)
      //  bottomNavigationView.setupWithNavController(navController)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
      //  val navController = findNavController(R.id.fragmentContainerView)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController




        NavigationUI.setupWithNavController(bottomNavigationView, navController)


    }
}