package com.hegunhee.newsimplememoapp.di

import com.hegunhee.newsimplememoapp.data.DB.provideDB
import com.hegunhee.newsimplememoapp.data.DB.provideToDoDao
import com.hegunhee.newsimplememoapp.domain.memoUsecase.AddMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoUsecase.GetMemoSortedByYearAndMonthUseCase
import com.hegunhee.newsimplememoapp.model.DefaultMemoRepository
import com.hegunhee.newsimplememoapp.model.MemoRepository
import com.hegunhee.newsimplememoapp.ui.addMemo.AddMemoViewModel
import com.hegunhee.newsimplememoapp.ui.memo.MemoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


internal val module = module{

    viewModel{MemoViewModel(get())}
    viewModel{AddMemoViewModel(get())}

    factory { GetMemoSortedByYearAndMonthUseCase(get()) }
    factory {AddMemoUseCase(get())}

    single<MemoRepository> { DefaultMemoRepository(get()) }

    single { provideDB(androidContext()) }
    single { provideToDoDao(get()) }
}