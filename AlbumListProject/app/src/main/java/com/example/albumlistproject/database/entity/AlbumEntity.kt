package com.example.albumlistproject.database.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlbumEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "userid") val userid: Int,
    @ColumnInfo(name = "title") val title: String
)