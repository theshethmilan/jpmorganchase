package com.milan.jpmorganchase.database.dbTables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblPhotos")
class TablePhotos(
    albumId: String?,
    photoId: String?,
    photoTitle: String?,
    photoUrl: String?,
    photoThumbnailUrl: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    var albumId: String? = null
    var photoId: String? = null
    var photoTitle: String? = null
    var photoUrl: String? = null
    var photoThumbnailUrl: String? = null

    init {
        this.albumId = albumId
        this.photoId = photoId
        this.photoTitle = photoTitle
        this.photoUrl = photoUrl
        this.photoThumbnailUrl = photoThumbnailUrl
    }

    companion object {
        val fieldAlbumId = "albumId"
        val fieldPhotoId = "photoId"
        val fieldPhotoTitle = "photoTitle"
        val fieldPhotoUrl = "photoUrl"
        val fieldPhotoThumbnailUrl = "photoThumbnailUrl"
    }
}