package com.milan.jpmorganchase.repository

import com.milan.jpmorganchase.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getAlbums() = apiHelper.getAlbums()

    suspend fun getPhotos(albumId: String) = apiHelper.getPhotos(albumId)
}