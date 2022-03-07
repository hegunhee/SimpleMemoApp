package com.hegunhee.newsimplememoapp.koinTest

import com.hegunhee.newsimplememoapp.domain.UseCase

class GetAllPeopleUseCase(val testRepository: KoinTestRepository): UseCase {
    suspend operator fun invoke() : List<People>{
        return testRepository.getAllPeople()
    }
}