package com.hegunhee.simplememoapp.di

import android.content.Context
import com.hegunhee.simplememoapp.data.DB.provideDB
import com.hegunhee.simplememoapp.data.DB.provideToDoDao
import com.hegunhee.simplememoapp.model.DefaultRepository
import com.hegunhee.simplememoapp.model.Repository
import com.hegunhee.simplememoapp.presentation.Main.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val module = module {

    single {Dispatchers.IO}
    single {Dispatchers.Main}

    viewModel { MainViewModel(get()) }


    single <Repository>{DefaultRepository(get(),get()) }

    single { provideDB(androidContext())}
    single { provideToDoDao(get())}
}