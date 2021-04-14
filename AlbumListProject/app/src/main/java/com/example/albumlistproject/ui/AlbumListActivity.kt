package com.example.albumlistproject.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.albumlistproject.R
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.albumlistproject.common.Status
import com.example.albumlistproject.adapters.AlbumListItemAdapter
import com.example.albumlistproject.database.DatabaseBuilder
import com.example.albumlistproject.database.DatabaseManagerImpl
import com.example.albumlistproject.models.Album
import com.example.albumlistproject.network.RetrofitInstance
import com.example.albumlistproject.viewmodels.AlbumListViewModel
import com.example.albumlistproject.viewmodels.factory.ViewModelFactory
import kotlinx.android.synthetic.main.activity_album_list.*

class AlbumListActivity : AppCompatActivity() {

    private lateinit var viewModel: AlbumListViewModel
    private lateinit var adapter: AlbumListItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_list)

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                RetrofitInstance.apiService,
                DatabaseManagerImpl(
                    DatabaseBuilder.getInstance(applicationContext)), isNetworkAvailable()
            )
        ).get(AlbumListViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AlbumListItemAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.albumList.observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        resource.data?.let { albums -> retrieveList(albums) }
                    }
                    Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(users: List<Album>) {
        adapter.apply {
            addItems(users)
            notifyDataSetChanged()
        }
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}