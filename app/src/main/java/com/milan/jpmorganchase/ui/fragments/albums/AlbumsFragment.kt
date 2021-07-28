package com.milan.jpmorganchase.ui.fragments.albums

import android.app.job.JobScheduler
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.milan.jpmorganchase.R
import com.milan.jpmorganchase.`interface`.ListClickListener
import com.milan.jpmorganchase.api.ApiStatus
import com.milan.jpmorganchase.database.dbTables.TableAlbums
import com.milan.jpmorganchase.databinding.FragmentAlbumsBinding
import com.milan.jpmorganchase.global.AppConst
import com.milan.jpmorganchase.models.AlbumResponseItem
import com.milan.jpmorganchase.ui.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@DelicateCoroutinesApi
@AndroidEntryPoint
class AlbumsFragment : BaseFragment<FragmentAlbumsBinding>() {
    private lateinit var albumAdapter: AlbumAdapter
    private val albumViewModel: AlbumFragmentViewModel by viewModels()

    override fun layoutId(): Int {
        return R.layout.fragment_albums
    }

    override fun initView(view: View) {
        activity?.title = getString(R.string.app_name)

        val listener = object : ListClickListener {
            override fun onClickListener(view: View, pos: Int, objects: Any) {
                val bundle = Bundle()
                bundle.putString(
                    AppConst.bundleAlbumsData,
                    gson.toJson(objects as AlbumResponseItem)
                )
                controller.navigate(R.id.fragmentPhotos, bundle)
            }
        }

        albumAdapter = AlbumAdapter(listener)

        getDataBinding().rvAlbums.apply {
            adapter = albumAdapter
            layoutManager = LinearLayoutManager(activityMain)
            addItemDecoration(DividerItemDecoration(activityMain, DividerItemDecoration.VERTICAL))
        }

        albumViewModel.albumRes.observe(this, Observer {
            when (it.ApiStatus) {
                ApiStatus.LOADING -> {
                    hideShowLayout(false)
                    activityMain.showLoading()
                }
                ApiStatus.SUCCESS -> {
                    hideShowLayout(true)
                    it.data.let { res ->
                        res?.let { album ->
                            album.sortWith(Comparator { o1, o2 -> o1.title.compareTo(o2.title) })
                            insertAlbum(album)
                            albumAdapter.addAlbumList(album)
                        }
                    }
                    activityMain.hideLoading()
                }
                ApiStatus.ERROR -> {
                    hideShowLayout(false)
                    activityMain.hideLoading()
                    Snackbar.make(
                        getDataBinding().viewAlbums,
                        it.error!!.trim(),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })

        getAlbumFromDb()
    }

    private fun getAlbumFromDb() {
        GlobalScope.launch(Dispatchers.Main) {
            myRoomDatabase.daoAlbums().getAlbums().let {
                if (it.isNotEmpty()) {
                    activityMain.showLoading()
                    val arrayListAlbumResponseItem = ArrayList<AlbumResponseItem>()
                    for (i in it.indices) {
                        arrayListAlbumResponseItem.add(
                            AlbumResponseItem(
                                it[i].albumId!!.toInt(),
                                it[i].albumTitle!!,
                                it[i].userId!!.toInt()
                            )
                        )
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        albumAdapter.addAlbumList(arrayListAlbumResponseItem)
                        activityMain.hideLoading()
                        hideShowLayout(true)
                    }, 2000)
                } else {
                    albumViewModel.getAlbumsList()
                }
            }
        }
    }

    private fun insertAlbum(albumList: ArrayList<AlbumResponseItem>) {
        myRoomDatabase.daoAlbums().deleteAlbums()
        for (i in albumList.indices) {
            myRoomDatabase.daoAlbums().insertAlbums(
                TableAlbums(
                    albumList[i].userId.toString().trim(),
                    albumList[i].id.toString().trim(),
                    albumList[i].title.trim()
                )
            )
        }
    }

    private fun hideShowLayout(isDataFound: Boolean) {
        getDataBinding().tvAblumsDataNotFound.visibility =
            if (isDataFound) View.GONE else View.VISIBLE
        getDataBinding().rvAlbums.visibility = if (isDataFound) View.VISIBLE else View.GONE
    }
}