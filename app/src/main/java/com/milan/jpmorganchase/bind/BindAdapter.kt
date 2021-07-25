package com.milan.jpmorganchase.bind

import androidx.databinding.BindingAdapter
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class BindAdapter {
    companion object {

        @JvmStatic
        @BindingAdapter(value = ["app:photoSrc"], requireAll = true)
        fun bindPhotos(imageView: ShapeableImageView, url: String?) {
            Picasso.get().load(url).into(imageView)
        }
    }
}