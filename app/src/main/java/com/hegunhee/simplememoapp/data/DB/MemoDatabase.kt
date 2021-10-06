package com.hegunhee.simplememoapp.data.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hegunhee.simplememoapp.data.Dao.DataDao
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity

@Database(entities = [accountItemEntity::class],version = 1,exportSchema = false)
abstract class MemoDatabase :RoomDatabase(){
    abstract fun dataDao() : DataDao

    companion object{
        const val DB_NAME = "MemoDataBase.db"
    }
}