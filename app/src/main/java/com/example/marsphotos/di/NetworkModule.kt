package com.example.marsphotos.di

import com.example.marsphotos.data.MarsApi
import com.example.marsphotos.data.MarsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMarsApiService(): MarsApiService {
        return MarsApi.retrofitService
    }
}
