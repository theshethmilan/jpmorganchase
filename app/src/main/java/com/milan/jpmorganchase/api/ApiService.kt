package com.milan.jpmorganchase.api

import com.milan.jpmorganchase.models.AlbumResponse
import com.milan.jpmorganchase.models.PhotosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("albums")
    suspend fun getAlbums(): Response<AlbumResponse>

    @GET("photos")
    suspend fun getPhotos(
        @Query("albumId") albumId: String
    ): Response<PhotosResponse>
}