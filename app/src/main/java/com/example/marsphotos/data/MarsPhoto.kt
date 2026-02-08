package com.example.marsphotos.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "marsphoto")
@Serializable
data class MarsPhoto(
    @PrimaryKey
    val id: String,
    @SerialName(value = "img_src")
    val imgSrc: String
)
