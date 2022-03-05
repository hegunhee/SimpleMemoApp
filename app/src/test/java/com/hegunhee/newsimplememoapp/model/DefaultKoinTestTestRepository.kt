package com.hegunhee.newsimplememoapp.model

import com.hegunhee.newsimplememoapp.data.People

class DefaultKoinTestTestRepository : KoinTestRepository {
    private val peopleList : MutableList<People> = mutableListOf<People>()
    override suspend fun sayHello(): String {
        return "Hello"
    }

    override suspend fun addPeople(people: People) {
        peopleList.add(people)
    }

    override suspend fun getAllPeople(): List<People> {
        return peopleList
    }
}