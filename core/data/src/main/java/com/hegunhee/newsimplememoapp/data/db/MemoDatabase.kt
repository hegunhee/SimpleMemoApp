package com.hegunhee.newsimplememoapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hegunhee.newsimplememoapp.data.dao.MemoDao
import com.hegunhee.newsimplememoapp.data.entity.MemoEntity

@Database(entities = [MemoEntity::class],version = 1,exportSchema = false)
abstract class MemoDatabase : RoomDatabase(){
    abstract fun memoDao():MemoDao

    companion object{
        const val DB_NAME = "memo.db"
    }
}