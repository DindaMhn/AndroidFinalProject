package com.example.androidfinalproject

import com.example.androidfinalproject.activity.MainActivity
import com.example.androidfinalproject.activity.provider.MenuProviderActivity
import com.example.androidfinalproject.activity.user.LoginUserActivity
import com.example.androidfinalproject.activity.user.MenuUserActivity
import com.example.androidfinalproject.recycleview.user.HistoryUserRecycleAdapter
import com.example.androidfinalproject.screens.provider.AddAssetFragment
import com.example.androidfinalproject.screens.provider.HomeProviderFragment
import com.example.androidfinalproject.screens.provider.LoginProviderFragment
import com.example.androidfinalproject.screens.provider.ProviderRegisterFragment
import com.example.androidfinalproject.screens.provider.*
import com.example.androidfinalproject.screens.user.*
import dagger.Component

@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(loginUserFragment: LoginUserFragment)
    fun inject(providerRegisterFragment: ProviderRegisterFragment)
    fun inject(userRegisterFragment: UserRegisterFragment)
    fun inject(homeUserFragment: HomeUserFragment)
    fun inject(menuUserActivity: MenuUserActivity)
    fun inject(homeProviderFragment: HomeProviderFragment)
    fun inject(menuProviderActivity: MenuProviderActivity)
    fun inject(topUpSaldoFragment: TopUpSaldoFragment)
    fun inject(profileUserFagment: ProfileUserFagment)
    fun inject(addAssetFragment: AddAssetFragment)
    fun inject(loginProviderFragment: LoginProviderFragment)
    fun inject(scanAssetQrCodeFragment: ScanAssetQrCodeFragment)
    fun inject(historyUserFragment: HistoryUserFragment)
    fun inject(historyUserRecycleAdapter: HistoryUserRecycleAdapter)
    fun inject(detailTicketFragment: DetailTicketFragment)
    fun inject(profileProviderFragment: ProfileProviderFragment)
    fun inject(loginUserActivity: LoginUserActivity)
}