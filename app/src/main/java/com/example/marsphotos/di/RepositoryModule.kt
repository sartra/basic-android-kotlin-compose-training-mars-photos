package com.example.marsphotos.di

import com.example.marsphotos.data.DefaultPhotoRepository
import com.example.marsphotos.data.MarsApiService
import com.example.marsphotos.data.MarsPhotosDao
import com.example.marsphotos.data.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePhotoRepository(
        marsApiService: MarsApiService,
        marsPhotosDao: MarsPhotosDao
    ): PhotoRepository {
        return DefaultPhotoRepository(marsApiService, marsPhotosDao)
    }
}
