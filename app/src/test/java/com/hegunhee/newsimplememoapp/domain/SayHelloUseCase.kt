package com.hegunhee.newsimplememoapp.domain

import com.hegunhee.newsimplememoapp.model.KoinTestRepository

class SayHelloUseCase(val testRepository: KoinTestRepository) : UseCase {
    suspend operator fun invoke() = testRepository.sayHello()
}