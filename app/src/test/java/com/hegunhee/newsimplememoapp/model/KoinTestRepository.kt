package com.hegunhee.newsimplememoapp.model

import com.hegunhee.newsimplememoapp.data.People

interface KoinTestRepository {
    suspend fun sayHello() : String

    suspend fun addPeople(people : People)

    suspend fun getAllPeople() : List<People>
}