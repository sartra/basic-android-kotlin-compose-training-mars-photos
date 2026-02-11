package com.example.marsphotos.data

import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    fun getMarsPhotos(): Flow<List<MarsPhoto>>
    suspend fun refreshPhotos()
}

class DefaultPhotoRepository(
    private val marsApiService: MarsApiService,
    private val marsPhotosDao: MarsPhotosDao
) : PhotoRepository {

    override fun getMarsPhotos(): Flow<List<MarsPhoto>> {
        return marsPhotosDao.getMarsPhotos()
    }

    override suspend fun refreshPhotos() {
        try {
            val photos = marsApiService.getPhotos()
            marsPhotosDao.insertAll(photos)
        } catch (e: Exception) {
            // Error handling is done in the ViewModel
            throw e
        }
    }
}
