package com.milan.jpmorganchase.ui.fragments.photos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.milan.jpmorganchase.R
import com.milan.jpmorganchase.`interface`.ListClickListener
import com.milan.jpmorganchase.databinding.RowPhotosBinding
import com.milan.jpmorganchase.models.PhotosResponseItem
import com.squareup.picasso.Picasso
import java.lang.Exception

class PhotosAdapter(
    private val listClickListener: ListClickListener,
) :
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    private val photosList = ArrayList<PhotosResponseItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_photos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = photosList[position]
        holder.binding?.objPhotos = data
        /*Uncomment below line if you're not using data binding adapter to load image*/
        /*Picasso.get().load(data.thumbnailUrl).into(holder.binding?.ivPhotos)*/
        holder.itemView.setOnClickListener {
            listClickListener.onClickListener(holder.itemView, position, data)
        }
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    fun addPhotosList(photosList: ArrayList<PhotosResponseItem>) {
        this.photosList.addAll(photosList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowPhotosBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }
}