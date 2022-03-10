package com.hegunhee.newsimplememoapp.di

import com.hegunhee.newsimplememoapp.domain.memoUsecase.*
import com.hegunhee.newsimplememoapp.model.MemoRepository
import com.hegunhee.newsimplememoapp.memoTest.TestMemoRepository
import com.hegunhee.newsimplememoapp.memoTest.MemoTestViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val memoTestModule = module {


    single { Dispatchers.IO }

    viewModel { MemoTestViewModel(get(), get(), get(), get(),get(),get()) }


    factory { GetAllMemoUseCase(get()) }
    factory { DeleteAllMemoUseCase(get()) }
    factory { DeleteMemoUseCase(get()) }
    factory { AddMemoUseCase(get()) }
    factory { AddMemoListUseCase(get()) }
    factory { GetMemoSortedByYearAndMonthUseCase(get()) }
    single<MemoRepository> { TestMemoRepository() }


}