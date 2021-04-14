package com.example.albumlistproject.database

import com.example.albumlistproject.database.entity.AlbumEntity

class DatabaseManagerImpl(private val appDatabase: AppDatabase) : DatabaseManager {

    override suspend fun getAlbumList(): List<AlbumEntity> = appDatabase.AlbumDao().getAll()

    override suspend fun insertAll(albumEntities: List<AlbumEntity>) = appDatabase.AlbumDao().insertAll(albumEntities)

    override suspend fun deleteAll() = appDatabase.AlbumDao().deleteAll()
}