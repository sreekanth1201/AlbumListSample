package com.example.albumlistproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.albumlistproject.database.dao.AlbumDao
import com.example.albumlistproject.database.entity.AlbumEntity

@Database(entities = [AlbumEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun AlbumDao(): AlbumDao

}