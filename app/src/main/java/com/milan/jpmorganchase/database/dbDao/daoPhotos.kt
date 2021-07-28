package com.milan.jpmorganchase.database.dbDao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.milan.jpmorganchase.database.dbTables.TablePhotos

@Dao
interface daoPhotos {
    @Insert
    fun insertPhotos(tablePhotos: TablePhotos)

    @Query("delete from tblPhotos")
    fun deletePhotos()

    @Query("select * from tblPhotos where albumId =:strAlbumId")
    suspend fun getPhotos(strAlbumId: String): List<TablePhotos>
}