package com.example.albumlistproject.database.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.albumlistproject.database.entity.AlbumEntity

@Dao
interface AlbumDao {

    @Query("SELECT * FROM albumentity")
    suspend fun getAll(): List<AlbumEntity>

    @Insert
    suspend fun insertAll(users: List<AlbumEntity>)

    @Query("DELETE FROM albumentity")
    suspend fun deleteAll()

}