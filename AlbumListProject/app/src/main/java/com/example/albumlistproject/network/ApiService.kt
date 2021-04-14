package com.example.albumlistproject.network

import com.example.albumlistproject.models.Album
import retrofit2.http.GET

interface ApiService {

    @GET("albums")
    suspend fun getAlbumList(): List<Album>

}