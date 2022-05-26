package com.hegunhee.newsimplememoapp.di

import com.hegunhee.newsimplememoapp.domain.memoUsecase.*
import com.hegunhee.newsimplememoapp.model.DefaultMemoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideDeleteAllMemoUseCase(repository: DefaultMemoRepository): DeleteAllMemoUseCase =
        DeleteAllMemoUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteMemoUseCase(repository: DefaultMemoRepository): DeleteMemoUseCase =
        DeleteMemoUseCase(repository)

    @Provides
    @Singleton
    fun provideGetAllMemoUseCase(repository: DefaultMemoRepository): GetAllMemoUseCase =
        GetAllMemoUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMemoListSortedByAttrYearMonthUseCase(repository: DefaultMemoRepository): GetMemoListSortedByAttrYearMonthUseCase =
        GetMemoListSortedByAttrYearMonthUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMemoAllSortedByYearAndMonthUseCase(repository: DefaultMemoRepository): GetMemoSortedByYearAndMonthUseCase =
        GetMemoSortedByYearAndMonthUseCase(repository)

    @Provides
    @Singleton
    fun provideGetStaticsDataUseCase(repository: DefaultMemoRepository): GetStaticsDataUseCase =
        GetStaticsDataUseCase(repository)

    @Provides
    @Singleton
    fun provideInsertMemoListUseCase(repository: DefaultMemoRepository): InsertMemoListUseCase =
        InsertMemoListUseCase(repository)

    @Provides
    @Singleton
    fun provideInsertMemoUseCase(repository: DefaultMemoRepository): InsertMemoUseCase =
        InsertMemoUseCase(repository)
}