package com.example.albumlistproject.network

class NetworkManager(private val apiService: ApiService) {
    suspend fun getAlbumList() = apiService.getAlbumList()
}