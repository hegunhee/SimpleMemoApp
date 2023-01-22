package com.hegunhee.newsimplememoapp.koinTest

class GetAllPeopleUseCase(val testRepository: KoinTestRepository) {
    suspend operator fun invoke() : List<People>{
        return testRepository.getAllPeople()
    }
}