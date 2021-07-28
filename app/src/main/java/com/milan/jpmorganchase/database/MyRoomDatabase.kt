package com.milan.jpmorganchase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.milan.jpmorganchase.database.dbDao.daoAlbums
import com.milan.jpmorganchase.database.dbDao.daoPhotos
import com.milan.jpmorganchase.database.dbTables.TableAlbums
import com.milan.jpmorganchase.database.dbTables.TablePhotos

@Database(
    entities = [
        TableAlbums::class,
        TablePhotos::class
    ],
    version = DbConst.dbVer,
    exportSchema = false
)
abstract class MyRoomDatabase : RoomDatabase() {
    companion object {
        private var myDBInstance: MyRoomDatabase? = null

        fun getDB(context: Context): MyRoomDatabase? {
            if (myDBInstance == null) {
                myDBInstance =
                    Room.databaseBuilder(context, MyRoomDatabase::class.java, DbConst.dbName)
                        .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                        .allowMainThreadQueries()
                        .build()
            }
            return myDBInstance
        }
    }

    abstract fun daoAlbums(): daoAlbums

    abstract fun daoPhotos(): daoPhotos
}