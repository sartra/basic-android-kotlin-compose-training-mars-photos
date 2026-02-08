package com.example.marsphotos.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Database(entities = [MarsPhoto::class], version = 1, exportSchema = false)
abstract class MarsPhotosDatabase : RoomDatabase() {

    abstract val dao: MarsPhotosDao
    
    companion object {
        @Volatile
        private var INSTANCE: MarsPhotosDatabase? = null
        
        fun getDatabase(context: Context): MarsPhotosDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarsPhotosDatabase::class.java,
                    "mars_photos_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Dao
interface MarsPhotosDao {
    
    @Query("SELECT * FROM marsphoto ORDER BY id ASC")
    fun getMarsPhotos(): Flow<List<MarsPhoto>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<MarsPhoto>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: MarsPhoto)
}