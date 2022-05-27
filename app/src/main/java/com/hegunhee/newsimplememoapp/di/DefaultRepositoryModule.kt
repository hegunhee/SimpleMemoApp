package com.hegunhee.newsimplememoapp.di

import com.hegunhee.newsimplememoapp.data.Dao.MemoDao
import com.hegunhee.newsimplememoapp.model.DefaultMemoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DefaultRepositoryModule {

    @Singleton
    @Provides
    fun provideDefaultRepository(
        dao : MemoDao
    ) : DefaultMemoRepository{
        return DefaultMemoRepository(dao)
    }
}