package com.example.albumlistproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.albumlistproject.R
import com.example.albumlistproject.models.Album
import kotlinx.android.synthetic.main.item_album_list_view.view.*

class AlbumListItemAdapter(private val albumList: ArrayList<Album>) : RecyclerView.Adapter<AlbumListItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(album: Album) {
            itemView.apply {
                album_title.text = album.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_album_list_view, parent, false))

    override fun getItemCount(): Int = albumList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumList[position])
    }

    fun addItems(albumList: List<Album>) {
        this.albumList.apply {
            clear()
            addAll(albumList)
        }
    }
}