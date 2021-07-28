package com.milan.jpmorganchase.ui.fragments.photos

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.milan.jpmorganchase.R
import com.milan.jpmorganchase.`interface`.ListClickListener
import com.milan.jpmorganchase.api.ApiStatus
import com.milan.jpmorganchase.database.dbTables.TablePhotos
import com.milan.jpmorganchase.databinding.FragmentPhotosBinding
import com.milan.jpmorganchase.global.AppConst
import com.milan.jpmorganchase.models.AlbumResponseItem
import com.milan.jpmorganchase.models.PhotosResponseItem
import com.milan.jpmorganchase.ui.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@AndroidEntryPoint
class PhotosFragment : BaseFragment<FragmentPhotosBinding>() {
    private lateinit var photosAdapter: PhotosAdapter
    private val photosViewModel: PhotosFragmentViewModel by viewModels()
    private lateinit var objAlbums: AlbumResponseItem
    private var albumID = ""

    override fun layoutId(): Int {
        return R.layout.fragment_photos
    }

    override fun initView(view: View) {
        objAlbums = gson.fromJson(
            arguments?.getString(AppConst.bundleAlbumsData, "{}"),
            AlbumResponseItem::class.java
        )
        albumID = objAlbums.id.toString().trim()
        activity?.title = "${objAlbums.title.trim()} $albumID"

        val listener = object : ListClickListener {
            override fun onClickListener(view: View, pos: Int, objects: Any) {
                val data = objects as PhotosResponseItem
                Snackbar.make(getDataBinding().viewPhotos, data.title.trim(), Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        photosAdapter = PhotosAdapter(listener)

        getDataBinding().rvPhotos.apply {
            adapter = photosAdapter
            layoutManager = GridLayoutManager(activityMain, 2)
            recycledViewPool.setMaxRecycledViews(0, 0)
        }

        photosViewModel.photosRes.observe(this, Observer {
            when (it.ApiStatus) {
                ApiStatus.LOADING -> {
                    hideShowLayout(false)
                    activityMain.showLoading()
                }
                ApiStatus.SUCCESS -> {
                    hideShowLayout(true)
                    it.data.let { res ->
                        res?.let { photos ->
                            insertPhotos(photos)
                            photosAdapter.addPhotosList(photos)
                        }
                    }
                    activityMain.hideLoading()
                }
                ApiStatus.ERROR -> {
                    hideShowLayout(false)
                    activityMain.hideLoading()
                    Snackbar.make(
                        getDataBinding().viewPhotos,
                        it.error!!.trim(),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })

        getPhotosFromDb()
    }

    private fun getPhotosFromDb() {
        GlobalScope.launch(Dispatchers.Main) {
            myRoomDatabase.daoPhotos().getPhotos(albumID).let {
                if (it.isNotEmpty()) {
                    activityMain.showLoading()
                    val arrayListPhotosResponseItem = ArrayList<PhotosResponseItem>()
                    for (i in it.indices) {
                        arrayListPhotosResponseItem.add(
                            PhotosResponseItem(
                                it[i].albumId!!.toInt(),
                                it[i].photoId!!.toInt(),
                                it[i].photoThumbnailUrl!!,
                                it[i].photoTitle!!,
                                it[i].photoUrl!!
                            )
                        )
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        photosAdapter.addPhotosList(arrayListPhotosResponseItem)
                        activityMain.hideLoading()
                        hideShowLayout(true)
                    }, 2000)
                } else {
                    photosViewModel.getPhotosList(albumID)
                }
            }
        }
    }

    private fun insertPhotos(photosList: ArrayList<PhotosResponseItem>) {
        for (i in photosList.indices) {
            myRoomDatabase.daoPhotos().insertPhotos(
                TablePhotos(
                    photosList[i].albumId.toString().trim(),
                    photosList[i].id.toString().trim(),
                    photosList[i].title.trim(),
                    photosList[i].url.trim(),
                    photosList[i].thumbnailUrl.trim()
                )
            )
        }
    }

    private fun hideShowLayout(isDataFound: Boolean) {
        getDataBinding().tvPhotosDataNotFound.visibility =
            if (isDataFound) View.GONE else View.VISIBLE
        getDataBinding().rvPhotos.visibility = if (isDataFound) View.VISIBLE else View.GONE
    }
}