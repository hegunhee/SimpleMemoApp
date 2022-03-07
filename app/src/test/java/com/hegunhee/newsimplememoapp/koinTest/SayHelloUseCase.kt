package com.hegunhee.newsimplememoapp.koinTest

import com.hegunhee.newsimplememoapp.domain.UseCase
import com.hegunhee.newsimplememoapp.koinTest.KoinTestRepository

class SayHelloUseCase(val testRepository: KoinTestRepository) : UseCase {
    suspend operator fun invoke() = testRepository.sayHello()
}