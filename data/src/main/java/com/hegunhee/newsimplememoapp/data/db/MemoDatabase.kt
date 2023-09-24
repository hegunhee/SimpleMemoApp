package com.hegunhee.newsimplememoapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hegunhee.newsimplememoapp.data.dao.CategoryDao
import com.hegunhee.newsimplememoapp.data.dao.MemoDao
import com.hegunhee.newsimplememoapp.data.entity.CategoryEntity
import com.hegunhee.newsimplememoapp.data.entity.MemoEntity

@Database(entities = [MemoEntity::class,CategoryEntity::class],version = 1,exportSchema = false)
abstract class MemoDatabase : RoomDatabase(){
    abstract fun memoDao():MemoDao

    abstract fun categoryDao() : CategoryDao

    companion object{
        const val DB_NAME = "memo.db"
    }
}