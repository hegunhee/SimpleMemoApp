package com.hegunhee.newsimplememoapp.koinTest

class AddPeopleUseCase(val testRepository : KoinTestRepository) {
    suspend operator fun invoke(people : People){
        testRepository.addPeople(people)
    }
}