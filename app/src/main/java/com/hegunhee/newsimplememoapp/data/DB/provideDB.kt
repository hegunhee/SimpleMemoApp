package com.hegunhee.newsimplememoapp.data.DB

import android.content.Context
import androidx.room.Room

fun provideDB(context : Context) : MemoDatabase =
    Room.databaseBuilder(context,MemoDatabase::class.java,MemoDatabase.DB_NAME).build()

fun provideToDoDao(database: MemoDatabase) = database.dao()