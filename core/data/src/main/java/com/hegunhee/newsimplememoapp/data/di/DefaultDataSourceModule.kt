package com.hegunhee.newsimplememoapp.data.di

import com.hegunhee.newsimplememoapp.data.dataSource.DefaultLocalDataSource
import com.hegunhee.newsimplememoapp.data.dataSource.DefaultRemoteDataSource
import com.hegunhee.newsimplememoapp.data.dataSource.LocalDataSource
import com.hegunhee.newsimplememoapp.data.dataSource.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DefaultDataSourceModule {

    @Singleton
    @Binds
    abstract fun provideDefaultLocalDataSource(
        defaultLocalDataSource: DefaultLocalDataSource
    ) : LocalDataSource

    @Singleton
    @Binds
    abstract fun provideDefaultRemoteDataSource(
        defaultRemoteDataSource: DefaultRemoteDataSource
    ) : RemoteDataSource
}