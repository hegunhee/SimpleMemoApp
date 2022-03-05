package com.hegunhee.newsimplememoapp

import com.hegunhee.newsimplememoapp.data.People
import com.hegunhee.newsimplememoapp.domain.AddPeopleUseCase
import com.hegunhee.newsimplememoapp.domain.GetAllPeopleUseCase
import com.hegunhee.newsimplememoapp.domain.SayHelloUseCase
import com.hegunhee.newsimplememoapp.model.DefaultKoinTestTestRepository
import com.hegunhee.newsimplememoapp.model.KoinTestRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class NoKoinTest {
    @Test
    fun hello(){
        runBlocking {
            val usecase = SayHelloUseCase(DefaultKoinTestTestRepository())
            assertEquals("Hello",usecase())
        }
    }
    @Test
    fun `add and get test`() :Unit= runBlocking{
        val peoples = List<People>(5){People("이름${it}",it)}
        val testRepository : KoinTestRepository = DefaultKoinTestTestRepository()
        val getUseCase = GetAllPeopleUseCase(testRepository)
        val addUseCase = AddPeopleUseCase(testRepository)
        for(i in 0 until 5){
            addUseCase(peoples[i])
        }
        assertEquals(peoples,getUseCase())
    }
}