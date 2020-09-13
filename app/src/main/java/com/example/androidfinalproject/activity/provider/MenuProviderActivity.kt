package com.example.androidfinalproject.activity.provider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.androidfinalproject.R
import kotlinx.android.synthetic.main.activity_menu_provider.*
import kotlinx.android.synthetic.main.activity_menu_user.*

class MenuProviderActivity : AppCompatActivity() {
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_provider)
        navController = (nav_host_fragment_menu_provider_container as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottom_navigation_provider, navController)
        bottom_navigation_provider.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeProvider -> {
                    navController.navigate(R.id.action_global_homeProviderFragment)
                    true
                }
                R.id.profileProvider -> {
                    navController.navigate(R.id.action_global_profileProviderFragment)
                    true
                }
                R.id.ratingProvider ->{
                    navController.navigate(R.id.action_global_assetRatingFragment)
                    true
                }
                else -> {
                    true
                }
            }
        }
    }
}