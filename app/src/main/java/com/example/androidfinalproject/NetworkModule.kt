package com.example.androidfinalproject

import com.example.androidfinalproject.config.RetrofitBuilder
import com.example.androidfinalproject.provider.account.ProviderAPI
import com.example.androidfinalproject.provider.asset.AssetAPI
import com.example.androidfinalproject.provider.home.ProviderHomeAPI
import com.example.androidfinalproject.provider.profile.ProviderProfileAPI
import com.example.androidfinalproject.user.account.UserAPI
import com.example.androidfinalproject.user.home.UserHomeAPI
import com.example.androidfinalproject.user.profile.UserProfileAPI
import com.example.androidfinalproject.user.search.LocationAPI
import com.example.androidfinalproject.user.ticket.TicketAPI
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    fun provideProviderAPI(): ProviderAPI {
        return RetrofitBuilder.createRetrofit().create(ProviderAPI::class.java)
    }
    @Provides
    fun provideUserAPI(): UserAPI {
        return RetrofitBuilder.createRetrofit().create(UserAPI::class.java)
    }
    @Provides
    fun provideUserHomeAPI():UserHomeAPI{
        return RetrofitBuilder.createRetrofit().create(UserHomeAPI::class.java)
    }
    @Provides
    fun provideProviderHomeAPI():ProviderHomeAPI{
        return RetrofitBuilder.createRetrofit().create(ProviderHomeAPI::class.java)
    }
    @Provides
    fun provideUserProfileAPI():UserProfileAPI{
        return RetrofitBuilder.createRetrofit().create(UserProfileAPI::class.java)
    }
    @Provides
    fun provideTicketAPI():TicketAPI{
        return RetrofitBuilder.createRetrofit().create(TicketAPI::class.java)
    }
    @Provides
    fun provideProviderProfile():ProviderProfileAPI{
        return RetrofitBuilder.createRetrofit().create(ProviderProfileAPI::class.java)
    }
    @Provides
    fun provideAssetAPI():AssetAPI{
        return RetrofitBuilder.createRetrofit().create(AssetAPI::class.java)
    }
    @Provides
    fun provideLocation():LocationAPI{
        return RetrofitBuilder.createRetrofit().create(LocationAPI::class.java)
    }
}
