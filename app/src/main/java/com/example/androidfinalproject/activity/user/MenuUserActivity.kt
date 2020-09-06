package com.example.androidfinalproject.activity.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.account.UserViewModel
import kotlinx.android.synthetic.main.activity_menu_user.*
import javax.inject.Inject


class MenuUserActivity : AppCompatActivity() {
    @Inject
    lateinit var userViewModel: UserViewModel
    lateinit var navController: NavController
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_user)
        sharedPreferences = this?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
        (applicationContext as MyApplication).applicationComponent.inject(this)
        navController = (nav_host_fragment_menu_user_container as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottom_navigation_user, navController)
        bottom_navigation_user.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeUser -> {
                    navController.navigate(R.id.action_global_homeUserFragment)
                    true
                }
                R.id.profileUser -> {
                    navController.navigate(
                        R.id.action_global_profileUserFagment)
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

}