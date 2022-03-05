package com.hegunhee.newsimplememoapp.domain

import com.hegunhee.newsimplememoapp.data.People
import com.hegunhee.newsimplememoapp.model.KoinTestRepository

class AddPeopleUseCase(val testRepository : KoinTestRepository) : UseCase {
    suspend operator fun invoke(people : People){
        testRepository.addPeople(people)
    }
}