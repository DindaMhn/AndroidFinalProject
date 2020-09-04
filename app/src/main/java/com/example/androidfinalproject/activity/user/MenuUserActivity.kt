package com.example.androidfinalproject.activity.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.androidfinalproject.R
import kotlinx.android.synthetic.main.activity_menu_user.*

class MenuUserActivity : AppCompatActivity() {
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_user)
        navController = (nav_host_fragment_menu_user_container as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottom_navigation_user, navController)
        bottom_navigation_user.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeUser -> {
                    navController.navigate(R.id.action_global_homeUserFragment)
                    true
                }
                R.id.scanUser -> {
                    navController.navigate(R.id.action_global_scanUserFragment)
                    true
                }
                else ->{
                    true
                }
            }
        }
    }
}