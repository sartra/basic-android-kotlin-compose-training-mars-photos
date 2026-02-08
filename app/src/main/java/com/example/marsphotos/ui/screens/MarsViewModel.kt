/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.marsphotos.ui.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.data.MarsApi
import com.example.marsphotos.data.MarsPhoto
import com.example.marsphotos.data.MarsPhotosDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException

class MarsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = MarsPhotosDatabase.getDatabase(application)
    private val dao = database.dao

    private val _marsUiState = MutableStateFlow<MarsUiState>(MarsUiState.Loading)
    val marsUiState: StateFlow<MarsUiState> = _marsUiState.asStateFlow()

    init {
        // Observe Room database for offline-first approach
        viewModelScope.launch {
            dao.getMarsPhotos()
                .catch { e ->
                    _marsUiState.value = MarsUiState.Error
                }
                .collect { photos ->
                    if (photos.isNotEmpty()) {
                        _marsUiState.value = MarsUiState.Success(photos = photos)
                    } else if (_marsUiState.value is MarsUiState.Loading) {
                        // Keep loading state if no cached data yet
                    }
                }
        }
        
        // Fetch from API and update database
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and saves to Room database.
     */
    fun getMarsPhotos() {
        viewModelScope.launch {
            try {
                val listResult = MarsApi.retrofitService.getPhotos()
                // Save to Room database
                dao.insertAll(listResult)
                // State will be updated automatically via Flow observation
            } catch (e: IOException) {
                // Only show error if we don't have cached data
                val cachedPhotos = dao.getMarsPhotos().first()
                if (cachedPhotos.isEmpty() && _marsUiState.value is MarsUiState.Loading) {
                    _marsUiState.value = MarsUiState.Error
                }
            }
        }
    }
}

sealed interface MarsUiState {
    data class Success(val photos: List<MarsPhoto>) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}