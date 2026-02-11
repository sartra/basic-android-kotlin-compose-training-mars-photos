package com.example.marsphotos.di

import android.content.Context
import androidx.room.Room
import com.example.marsphotos.data.MarsPhotosDao
import com.example.marsphotos.data.MarsPhotosDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MarsPhotosDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MarsPhotosDatabase::class.java,
            "mars_photos_database"
        ).build()
    }

    @Provides
    fun provideMarsPhotosDao(database: MarsPhotosDatabase): MarsPhotosDao {
        return database.dao
    }
}
