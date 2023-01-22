package com.hegunhee.newsimplememoapp.koinTest

class SayHelloUseCase(val testRepository: KoinTestRepository){
    suspend operator fun invoke() = testRepository.sayHello()
}