package com.example.androidfinalproject

import com.example.androidfinalproject.config.RetrofitBuilder
import com.example.androidfinalproject.provider.ProviderAPI
import com.example.androidfinalproject.user.UserAPI
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    fun provideProviderAPI():ProviderAPI{
        return RetrofitBuilder.createRetrofit().create(ProviderAPI::class.java)
    }
    @Provides
    fun provideUserAPI(): UserAPI {
        return RetrofitBuilder.createRetrofit().create(UserAPI::class.java)
    }
}
