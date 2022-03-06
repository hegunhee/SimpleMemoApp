package com.hegunhee.newsimplememoapp.di

import com.hegunhee.newsimplememoapp.MemoTest
import com.hegunhee.newsimplememoapp.domain.UseCase
import com.hegunhee.newsimplememoapp.domain.memoTest.AddMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoTest.DeleteAllMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoTest.DeleteMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoTest.GetAllMemoUseCase
import com.hegunhee.newsimplememoapp.model.MemoRepository
import com.hegunhee.newsimplememoapp.model.TestMemoRepository
import com.hegunhee.newsimplememoapp.viewmodel.MemoTestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val memoTestModule = module {


    viewModel{ MemoTestViewModel(get(),get(),get(),get()) }


    factory{ GetAllMemoUseCase(get()) }
    factory { DeleteAllMemoUseCase(get()) }
    factory { DeleteMemoUseCase(get()) }
    factory{ AddMemoUseCase(get()) }

    single<MemoRepository> { TestMemoRepository() }
}