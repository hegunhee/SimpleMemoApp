package com.hegunhee.simplememoapp.di

import com.hegunhee.simplememoapp.data.DB.provideDB
import com.hegunhee.simplememoapp.data.DB.provideToDoDao
import com.hegunhee.simplememoapp.domain.product.GetAllMemoUseCase
import com.hegunhee.simplememoapp.domain.product.InsertOneMemoUseCase
import com.hegunhee.simplememoapp.model.DefaultRepository
import com.hegunhee.simplememoapp.model.Repository
import com.hegunhee.simplememoapp.presentation.Memo.MemoViewModel
import com.hegunhee.simplememoapp.presentation.Statis.StatisViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val module = module {

    single { Dispatchers.IO }
    single { Dispatchers.Main }

    factory {GetAllMemoUseCase(get())}
    factory {InsertOneMemoUseCase(get())}

    viewModel { MemoViewModel(get(),get()) }
    viewModel { StatisViewModel() }


    single<Repository> { DefaultRepository(get(), get()) }

    single { provideDB(androidContext()) }
    single { provideToDoDao(get()) }
}