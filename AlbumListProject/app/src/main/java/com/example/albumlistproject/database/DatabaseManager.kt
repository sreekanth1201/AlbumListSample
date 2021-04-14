package com.example.albumlistproject.database

import com.example.albumlistproject.database.entity.AlbumEntity

interface DatabaseManager {

    suspend fun getAlbumList(): List<AlbumEntity>

    suspend fun insertAll(albumEntities: List<AlbumEntity>)

    suspend fun deleteAll()

}