package com.example.androidfinalproject

import com.example.androidfinalproject.config.RetrofitBuilder
import com.example.androidfinalproject.provider.account.ProviderAPI
import com.example.androidfinalproject.provider.home.ProviderHomeAPI
import com.example.androidfinalproject.user.account.UserAPI
import com.example.androidfinalproject.user.home.UserHomeAPI
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
}
