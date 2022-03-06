package com.hegunhee.newsimplememoapp.domain.koinTest

import com.hegunhee.newsimplememoapp.data.People
import com.hegunhee.newsimplememoapp.domain.UseCase
import com.hegunhee.newsimplememoapp.model.KoinTestRepository

class GetAllPeopleUseCase(val testRepository: KoinTestRepository): UseCase {
    suspend operator fun invoke() : List<People>{
        return testRepository.getAllPeople()
    }
}