package com.example.androidfinalproject.activity.user

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import androidx.navigation.ui.NavigationUI
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.UserViewModel
import kotlinx.android.synthetic.main.activity_menu_user.*
import javax.inject.Inject


class MenuUserActivity : AppCompatActivity() {
    @Inject
    lateinit var userViewModel: UserViewModel
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_user)
        (applicationContext as MyApplication).applicationComponent.inject(this)
        navController = (nav_host_fragment_menu_user_container as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottom_navigation_user, navController)
//        val id = intent.getStringExtra("id_user")
//        println("id from intent"+id)


        bottom_navigation_user.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeUser -> {
//                    val bundle = bundleOf(Pair("user_id",id))
                    navController.navigate(R.id.action_global_homeUserFragment)
                    true
                }
                else ->{
                    true
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        println("ON START")
        userViewModel.userData.observe(
            this, Observer {
                println("ID MENU USER"+it.id)
            })
    }
}