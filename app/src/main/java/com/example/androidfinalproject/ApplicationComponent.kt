package com.example.androidfinalproject

import com.example.androidfinalproject.activity.MainActivity
import com.example.androidfinalproject.activity.provider.MenuProviderActivity
import com.example.androidfinalproject.activity.user.MenuUserActivity
import com.example.androidfinalproject.screens.LoginFragment
import com.example.androidfinalproject.screens.provider.HomeProviderFragment
import com.example.androidfinalproject.screens.provider.ProviderRegisterFragment
import com.example.androidfinalproject.screens.user.HomeUserFragment
import com.example.androidfinalproject.screens.user.UserRegisterFragment
import dagger.Component

@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(loginFragment: LoginFragment)
    fun inject(providerRegisterFragment: ProviderRegisterFragment)
    fun inject(userRegisterFragment: UserRegisterFragment)
    fun inject(homeUserFragment: HomeUserFragment)
    fun inject(menuUserActivity: MenuUserActivity)
    fun inject(homeProviderFragment: HomeProviderFragment)
    fun inject(menuProviderActivity: MenuProviderActivity)
}