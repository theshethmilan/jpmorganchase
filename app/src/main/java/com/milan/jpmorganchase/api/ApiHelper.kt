package com.milan.jpmorganchase.api

import com.milan.jpmorganchase.models.AlbumResponse
import com.milan.jpmorganchase.models.PhotosResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getAlbums():Response<AlbumResponse>

    suspend fun getPhotos(albumId: String):Response<PhotosResponse>
}