package com.example.marsphotos.data


interface PhotoRepository {
    suspend fun getPhotos(): List<MarsPhoto>
}

class PhotoRepositoryImpl(private val marsApiService: MarsApiService) : PhotoRepository {

    override suspend fun getPhotos(): List<MarsPhoto> {
        return marsApiService.getPhotos()
    }
}

