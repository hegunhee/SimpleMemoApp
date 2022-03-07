package com.hegunhee.newsimplememoapp.di

import android.app.Application
import com.hegunhee.newsimplememoapp.data.DB.provideDB
import com.hegunhee.newsimplememoapp.data.DB.provideToDoDao
import com.hegunhee.newsimplememoapp.domain.memoUsecase.AddMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoUsecase.DeleteAllMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoUsecase.DeleteMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoUsecase.GetAllMemoUseCase
import com.hegunhee.newsimplememoapp.model.MemoRepository
import com.hegunhee.newsimplememoapp.model.TestMemoRepository
import com.hegunhee.newsimplememoapp.viewmodel.MemoTestViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val memoTestModule = module {


    single { Dispatchers.IO }
    viewModel { MemoTestViewModel(get(), get(), get(), get()) }


    factory { GetAllMemoUseCase(get()) }
    factory { DeleteAllMemoUseCase(get()) }
    factory { DeleteMemoUseCase(get()) }
    factory { AddMemoUseCase(get()) }

    single<MemoRepository> { TestMemoRepository(get(),get()) }

    single { provideDB(androidContext()) }
    single { provideToDoDao(get()) }

}