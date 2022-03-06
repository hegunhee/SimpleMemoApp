package com.hegunhee.newsimplememoapp.domain.koinTest

import com.hegunhee.newsimplememoapp.domain.UseCase
import com.hegunhee.newsimplememoapp.model.KoinTestRepository

class SayHelloUseCase(val testRepository: KoinTestRepository) : UseCase {
    suspend operator fun invoke() = testRepository.sayHello()
}