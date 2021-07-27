package com.milan.jpmorganchase.ui.fragments.photos

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.milan.jpmorganchase.R
import com.milan.jpmorganchase.`interface`.ListClickListener
import com.milan.jpmorganchase.api.ApiStatus
import com.milan.jpmorganchase.databinding.FragmentPhotosBinding
import com.milan.jpmorganchase.global.AppConst
import com.milan.jpmorganchase.models.AlbumResponseItem
import com.milan.jpmorganchase.models.PhotosResponseItem
import com.milan.jpmorganchase.ui.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosFragment : BaseFragment<FragmentPhotosBinding>() {
    private lateinit var photosAdapter: PhotosAdapter
    private val photosViewModel: PhotosFragmentViewModel by viewModels()

    override fun layoutId(): Int {
        return R.layout.fragment_photos
    }

    override fun initView(view: View) {
        val objAlbums = gson.fromJson(
            arguments?.getString(AppConst.bundleAlbumsData, "{}"),
            AlbumResponseItem::class.java
        )
        photosViewModel.getPhotosList(objAlbums.id.toString())
        activity?.title = objAlbums.title.trim()

        val listener = object : ListClickListener {
            override fun onClickListener(view: View, pos: Int, objects: Any) {
                val data = objects as PhotosResponseItem
                Snackbar.make(getDataBinding().viewPhotos, data.title.trim(), Snackbar.LENGTH_SHORT).show()
            }
        }

        photosAdapter = PhotosAdapter(listener)

        getDataBinding().rvPhotos.apply {
            adapter = photosAdapter
            layoutManager = GridLayoutManager(activityMain, 2)
            recycledViewPool.setMaxRecycledViews(0, 0)
        }

        photosViewModel.vmPhotosRes.observe(this, Observer {
            when (it.ApiStatus) {
                ApiStatus.LOADING -> {
                    hideShowLayout(false)
                    activityMain.showLoading()
                }
                ApiStatus.SUCCESS -> {
                    hideShowLayout(true)
                    it.data.let { res ->
                        res?.let { photos ->
                            photosAdapter.addPhotosList(photos)
                        }
                    }
                    activityMain.hideLoading()
                }
                ApiStatus.ERROR -> {
                    hideShowLayout(false)
                    activityMain.hideLoading()
                    Snackbar.make(getDataBinding().viewPhotos, it.error!!.trim(), Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun hideShowLayout(isDataFound: Boolean) {
        getDataBinding().tvPhotosDataNotFound.visibility = if (isDataFound) View.GONE else View.VISIBLE
        getDataBinding().rvPhotos.visibility = if (isDataFound) View.VISIBLE else View.GONE
    }
}