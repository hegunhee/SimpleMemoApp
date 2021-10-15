package com.hegunhee.simplememoapp.di

import com.hegunhee.simplememoapp.data.DB.provideDB
import com.hegunhee.simplememoapp.data.DB.provideToDoDao
import com.hegunhee.simplememoapp.domain.product.DeleteOneMemoUseCase
import com.hegunhee.simplememoapp.domain.product.GetAllSortedMemoUseCase
import com.hegunhee.simplememoapp.domain.product.InsertOneMemoUseCase
import com.hegunhee.simplememoapp.model.DefaultRepository
import com.hegunhee.simplememoapp.model.Repository
import com.hegunhee.simplememoapp.presentation.Memo.MemoViewModel
import com.hegunhee.simplememoapp.presentation.Statis.StatisViewModel
import com.hegunhee.simplememoapp.presentation.showMemo.ShowMemoViewModel
import com.hegunhee.simplememoapp.presentation.testMemo.TestMemoViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val module = module {

    single { Dispatchers.IO }
    single { Dispatchers.Main }

    factory {GetAllSortedMemoUseCase(get())}
    factory {InsertOneMemoUseCase(get())}
    factory { DeleteOneMemoUseCase(get()) }

    viewModel { MemoViewModel(get(),get()) }
    viewModel { StatisViewModel() }
    viewModel { ShowMemoViewModel()}
    viewModel { TestMemoViewModel(get(),get()) }


    single<Repository> { DefaultRepository(get(), get()) }

    single { provideDB(androidContext()) }
    single { provideToDoDao(get()) }
}