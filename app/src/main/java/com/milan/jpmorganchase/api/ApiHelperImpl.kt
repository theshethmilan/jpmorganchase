package com.milan.jpmorganchase.api

import com.milan.jpmorganchase.models.AlbumResponse
import com.milan.jpmorganchase.models.PhotosResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getAlbums(): Response<AlbumResponse> = apiService.getAlbums()

    override suspend fun getPhotos(albumId: String): Response<PhotosResponse> = apiService.getPhotos(albumId)
}