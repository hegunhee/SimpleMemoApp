package com.hegunhee.newsimplememoapp.data.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hegunhee.newsimplememoapp.data.Dao.MemoDao
import com.hegunhee.newsimplememoapp.data.entity.Memo

@Database(entities = [Memo::class],version = 1,exportSchema = false)
abstract class MemoDatabase : RoomDatabase(){
    abstract fun dao():MemoDao

    companion object{
        const val DB_NAME = "memo.db"
    }
}