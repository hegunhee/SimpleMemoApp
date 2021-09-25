package com.hegunhee.simplememoapp.di

import com.hegunhee.simplememoapp.presentation.Main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val module = module {

    viewModel { MainViewModel() }

}