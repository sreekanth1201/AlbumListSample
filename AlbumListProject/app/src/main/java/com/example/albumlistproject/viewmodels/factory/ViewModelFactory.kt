package com.example.albumlistproject.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.albumlistproject.database.DatabaseManager
import com.example.albumlistproject.network.ApiService
import com.example.albumlistproject.network.NetworkManager
import com.example.albumlistproject.viewmodels.AlbumListViewModel

class ViewModelFactory(
    private val apiService: ApiService,
    private val databaseManager: DatabaseManager,
    private val isNetworkAvailable: Boolean
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumListViewModel::class.java)) {
            return AlbumListViewModel(NetworkManager(apiService), databaseManager, isNetworkAvailable) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}