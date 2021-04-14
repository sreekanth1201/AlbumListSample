package com.example.albumlistproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.albumlistproject.common.Resource
import com.example.albumlistproject.database.DatabaseManager
import com.example.albumlistproject.database.entity.AlbumEntity
import com.example.albumlistproject.models.Album
import com.example.albumlistproject.network.NetworkManager
import com.example.albumlistproject.viewmodels.AlbumListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AbumListViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineRule()

    @Mock
    private lateinit var networkManager: NetworkManager

    @Mock
    private lateinit var databaseManager: DatabaseManager

    @Mock
    private lateinit var apiObserver: Observer<Resource<List<Album>>>


    @Before
    fun setUp() {
        // do something if required
    }

    @Test
    fun fetchAlbumList_api_success() {
        val dummyAlbumList = getAlbumList()
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(dummyAlbumList)
                .`when`(networkManager)
                .getAlbumList()
            val viewModel = AlbumListViewModel(networkManager, databaseManager, true)
            viewModel.fetchAlbumList().observeForever(apiObserver)
            Mockito.verify(apiObserver).onChanged(Resource.success(dummyAlbumList))
            viewModel.fetchAlbumList().removeObserver(apiObserver)
        }
    }

    @Test
    fun fetchAlbumList_api_failure() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(networkManager)
                .getAlbumList()
            val viewModel = AlbumListViewModel(networkManager, databaseManager, true)
            viewModel.fetchAlbumList().observeForever(apiObserver)
            Mockito.verify(apiObserver).onChanged(
                Resource.error(data = null, message = errorMessage)
            )
            viewModel.fetchAlbumList().removeObserver(apiObserver)
        }
    }

    @Test
    fun fetchAlbumList_api_offline_scenario() {
        val dummyAlbumList = getAlbumList()
        val dummyAlbumEntity = getAlbumEntityList()
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(dummyAlbumEntity)
                .`when`(databaseManager)
                .getAlbumList()
            val viewModel = AlbumListViewModel(networkManager, databaseManager, false)
            viewModel.fetchAlbumList().observeForever(apiObserver)
            Mockito.verify(apiObserver).onChanged(Resource.success(dummyAlbumList))
            viewModel.fetchAlbumList().removeObserver(apiObserver)
        }
    }

    fun getAlbumList(): List<Album> {
        var albumList = mutableListOf<Album>()
        albumList.add(Album(1,1,"quidem molestiae enim"))
        albumList.add(Album(2,2,"sunt qui excepturi placeat culpa"))
        albumList.add(Album(3,3,"eaque aut omnis a"))
        return albumList.sortedWith(compareBy({ it.title }))
    }

    fun getAlbumEntityList(): List<AlbumEntity> {
        var albumList = mutableListOf<AlbumEntity>()
        albumList.add(AlbumEntity(1,1,"quidem molestiae enim"))
        albumList.add(AlbumEntity(2,2,"sunt qui excepturi placeat culpa"))
        albumList.add(AlbumEntity(3,3,"eaque aut omnis a"))
        return albumList.sortedWith(compareBy({ it.title }))
    }
}