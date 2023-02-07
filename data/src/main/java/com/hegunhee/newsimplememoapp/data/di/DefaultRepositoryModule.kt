package com.hegunhee.newsimplememoapp.data.di

import com.hegunhee.newsimplememoapp.data.repository.DefaultMemoRepository
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class DefaultRepositoryModule {

    @Singleton
    @Binds
    abstract fun provideDefaultRepository(
        defaultMemoRepository: DefaultMemoRepository
    ) : MemoRepository
}