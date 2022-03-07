package com.hegunhee.newsimplememoapp.koinTest

interface KoinTestRepository {
    suspend fun sayHello() : String

    suspend fun addPeople(people : People)

    suspend fun getAllPeople() : List<People>
}