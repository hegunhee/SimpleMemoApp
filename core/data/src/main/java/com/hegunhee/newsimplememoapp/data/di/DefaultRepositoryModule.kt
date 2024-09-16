package com.hegunhee.newsimplememoapp.data.di

import com.hegunhee.newsimplememoapp.data.repository.DefaultCategoryRepository
import com.hegunhee.newsimplememoapp.data.repository.DefaultMemoRepository
import com.hegunhee.newsimplememoapp.domain.repository.CategoryRepository
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
    abstract fun provideDefaultCategoryRepository(
        defaultCategoryRepository: DefaultCategoryRepository
    ) : CategoryRepository

    @Singleton
    @Binds
    abstract fun provideDefaultMemoRepository(
        defaultMemoTempRepository: DefaultMemoRepository
    ) : MemoRepository
}