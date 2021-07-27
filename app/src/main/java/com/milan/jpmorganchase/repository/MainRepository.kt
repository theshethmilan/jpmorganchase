package com.milan.jpmorganchase.repository

import com.milan.jpmorganchase.api.ApiService
import com.milan.jpmorganchase.models.AlbumResponse
import com.milan.jpmorganchase.models.PhotosResponse
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getAlbums(): Response<AlbumResponse> = apiService.getAlbums()

    suspend fun getPhotos(albumId: String) : Response<PhotosResponse> = apiService.getPhotos(albumId)
}