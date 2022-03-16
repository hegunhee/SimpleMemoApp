package com.hegunhee.newsimplememoapp.di

import com.hegunhee.newsimplememoapp.ui.memo.MemoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module{

    single<MemoViewModel>{MemoViewModel()}
}