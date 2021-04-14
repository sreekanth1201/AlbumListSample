package com.example.albumlistproject.viewmodels

import androidx.lifecycle.*
import com.example.albumlistproject.common.Resource
import com.example.albumlistproject.database.DatabaseManager
import com.example.albumlistproject.database.entity.AlbumEntity
import com.example.albumlistproject.models.Album
import com.example.albumlistproject.network.NetworkManager
import kotlinx.coroutines.launch

class AlbumListViewModel(
    private val networkManager: NetworkManager,
    private val databaseManager: DatabaseManager,
    private val isNetworkAvailable: Boolean
) : ViewModel() {

    val albumList = MutableLiveData<Resource<List<Album>>>()

    init {
        if(isNetworkAvailable) fetchAlbumListFromAPI() else fetchAlbumListFromDB()
    }

    /**
     * Makes API call and fetch album information and store into the database
     */
    private fun fetchAlbumListFromAPI() {
        viewModelScope.launch {
            albumList.postValue(Resource.loading(emptyList()))
            try {
                val albumListResponse = networkManager.getAlbumList()
                val sortedAlbumList = albumListResponse.sortedWith(compareBy({ it.title }))
                updateDB(sortedAlbumList)
                albumList.postValue(Resource.success(sortedAlbumList))
            } catch (e: Exception) {
                albumList.postValue(
                    Resource.error(
                        data = null,
                        message = e.message ?: "Error Occurred!"
                    )
                )
            }
        }
    }

    private suspend fun updateDB(sortedAlbumList: List<Album>) {
        val albumsDBList = mutableListOf<AlbumEntity>()
        databaseManager.deleteAll()
        for (album in sortedAlbumList) {
            val item = AlbumEntity(
                id = album.id,
                userid = album.userId,
                title = album.title
            )
            albumsDBList.add(item)
        }
        databaseManager.insertAll((albumsDBList))
    }

    /**
     * Fetches data from local database and notify activity to display offline data available
     * this will be triggerred in offline case.
     */
    private fun fetchAlbumListFromDB() {
        viewModelScope.launch {
            val albumListResults = databaseManager.getAlbumList()
            val albumDataList = mutableListOf<Album>()
            for (album in albumListResults) {
                val item = Album(
                    userId = album.userid,
                    id = album.id,
                    title = album.title
                )
                albumDataList.add(item)
            }
            albumList.postValue(Resource.success(albumDataList))
        }
    }

    fun fetchAlbumList(): LiveData<Resource<List<Album>>> {
        return albumList
    }
}