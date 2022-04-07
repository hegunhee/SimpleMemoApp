package com.hegunhee.newsimplememoapp.di

import com.hegunhee.newsimplememoapp.data.DB.provideDB
import com.hegunhee.newsimplememoapp.data.DB.provideToDoDao
import com.hegunhee.newsimplememoapp.domain.memoUsecase.InsertMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoUsecase.DeleteMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoUsecase.GetMemoSortedByYearAndMonthUseCase
import com.hegunhee.newsimplememoapp.domain.memoUsecase.GetStaticsDataUseCase
import com.hegunhee.newsimplememoapp.model.DefaultMemoRepository
import com.hegunhee.newsimplememoapp.model.MemoRepository
import com.hegunhee.newsimplememoapp.ui.addMemo.AddMemoViewModel
import com.hegunhee.newsimplememoapp.ui.detailMemo.DetailMemoViewModel
import com.hegunhee.newsimplememoapp.ui.detailStatics.DetaiStaticsViewModel
import com.hegunhee.newsimplememoapp.ui.memo.MemoViewModel
import com.hegunhee.newsimplememoapp.ui.statics.StaticViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


internal val module = module{

    viewModel{DetailMemoViewModel(get(),get())}
    viewModel{MemoViewModel(get())}
    viewModel{AddMemoViewModel(get())}
    viewModel{StaticViewModel(get())}
    viewModel{DetaiStaticsViewModel()}

    factory { GetMemoSortedByYearAndMonthUseCase(get()) }
    factory {InsertMemoUseCase(get())}
    factory {DeleteMemoUseCase(get())}
    factory {GetStaticsDataUseCase(get())}

    single<MemoRepository> { DefaultMemoRepository(get()) }

    single { provideDB(androidContext()) }
    single { provideToDoDao(get()) }
}