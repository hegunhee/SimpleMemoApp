package com.hegunhee.newsimplememoapp.di

import com.hegunhee.newsimplememoapp.koinTest.AddPeopleUseCase
import com.hegunhee.newsimplememoapp.koinTest.GetAllPeopleUseCase
import com.hegunhee.newsimplememoapp.koinTest.SayHelloUseCase
import com.hegunhee.newsimplememoapp.koinTest.DefaultKoinTestTestRepository
import com.hegunhee.newsimplememoapp.koinTest.KoinTestRepository
import org.koin.dsl.module

val koinTestModule = module {

    factory { SayHelloUseCase(get()) }
    factory { AddPeopleUseCase(get()) }
    factory { GetAllPeopleUseCase(get()) }

    single<KoinTestRepository> { DefaultKoinTestTestRepository() }
}