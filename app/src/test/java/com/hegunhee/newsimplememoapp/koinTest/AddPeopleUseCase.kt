package com.hegunhee.newsimplememoapp.koinTest

import com.hegunhee.newsimplememoapp.domain.UseCase

class AddPeopleUseCase(val testRepository : KoinTestRepository) : UseCase {
    suspend operator fun invoke(people : People){
        testRepository.addPeople(people)
    }
}