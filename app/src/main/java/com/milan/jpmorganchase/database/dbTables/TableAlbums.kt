package com.milan.jpmorganchase.database.dbTables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblAlbums")
class TableAlbums(userId: String?, albumId: String?, albumTitle: String?) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    var userId: String? = null
    var albumId: String? = null
    var albumTitle: String? = null

    init {
        this.userId = userId
        this.albumId = albumId
        this.albumTitle = albumTitle
    }

    companion object {
        var fieldUserId = "userId"
        var fieldAlbumId = "albumId"
        var fieldAlbumTitle = "albumTitle"
    }
}