package com.hegunhee.newsimplememoapp.di

import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import com.hegunhee.newsimplememoapp.model.DefaultMemoRepository
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