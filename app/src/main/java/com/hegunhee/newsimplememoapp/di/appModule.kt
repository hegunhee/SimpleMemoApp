package com.hegunhee.newsimplememoapp.di

import com.hegunhee.newsimplememoapp.ui.memo.MemoViewModel
import org.koin.dsl.module


internal val module = module{

    factory{MemoViewModel()}
}