package com.milan.jpmorganchase.database.dbDao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.milan.jpmorganchase.database.dbTables.TableAlbums

@Dao
interface daoAlbums {
    @Insert
    fun insertAlbums(tableAlbums: TableAlbums?)

    @Query("delete from tblAlbums")
    fun deleteAlbums()

    @Query("select * from tblAlbums")
    fun getAlbums(): TableAlbums
}