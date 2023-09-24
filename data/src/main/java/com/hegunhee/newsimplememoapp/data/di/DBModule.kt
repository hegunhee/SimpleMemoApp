package com.hegunhee.newsimplememoapp.data.di

import android.content.Context
import androidx.room.Room
import com.hegunhee.newsimplememoapp.data.dao.CategoryDao
import com.hegunhee.newsimplememoapp.data.db.MemoDatabase
import com.hegunhee.newsimplememoapp.data.dao.MemoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBModule {

    @Singleton
    @Provides
    fun provideMemoDatabase(
        @ApplicationContext context: Context
    ): MemoDatabase = Room
        .databaseBuilder(context, MemoDatabase::class.java, MemoDatabase.DB_NAME)
        .build()

    @Singleton
    @Provides
    fun provideMemoDao(memoDatabase: MemoDatabase) : MemoDao = memoDatabase.memoDao()

    @Singleton
    @Provides
    fun provideCategoryDao(memoDatabase: MemoDatabase) : CategoryDao = memoDatabase.categoryDao()
}