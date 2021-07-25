package com.milan.jpmorganchase.ui.fragments.albums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.milan.jpmorganchase.R
import com.milan.jpmorganchase.`interface`.ListClickListener
import com.milan.jpmorganchase.databinding.RowAlbumsBinding
import com.milan.jpmorganchase.models.AlbumResponseItem
import kotlin.collections.ArrayList

class AlbumAdapter(private val listClickListener: ListClickListener) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    private val albumList = ArrayList<AlbumResponseItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_albums, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = albumList[position]
        holder.binding?.objAlbums = data
        holder.itemView.setOnClickListener {
            listClickListener.onClickListener(holder.itemView, position, data)
        }
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    fun addAlbumList(albumList: ArrayList<AlbumResponseItem>) {
        this.albumList.addAll(albumList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowAlbumsBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }
}