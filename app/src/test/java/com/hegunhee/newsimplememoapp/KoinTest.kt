package com.hegunhee.newsimplememoapp

import android.app.Application
import com.hegunhee.newsimplememoapp.data.People
import com.hegunhee.newsimplememoapp.di.koinTestModule
import com.hegunhee.newsimplememoapp.domain.koinTest.AddPeopleUseCase
import com.hegunhee.newsimplememoapp.domain.koinTest.GetAllPeopleUseCase
import com.hegunhee.newsimplememoapp.domain.koinTest.SayHelloUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import kotlin.test.assertEquals

class TestKoin : KoinTest {

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()
    @Mock
    private lateinit var context : Application

    private val dispatcher = TestCoroutineDispatcher()

    private val addUsecase : AddPeopleUseCase by inject()
    private val getAllUsecase : GetAllPeopleUseCase by inject()
    private val sayHelloUseCase : SayHelloUseCase by inject()

    @Before
    fun setUp(){
        startKoin {
            androidContext(context)
            modules(koinTestModule)
        }
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown(){
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun hello() = runBlockingTest{
        assertEquals(sayHelloUseCase(),"Hello")
    }

    @Test
    fun `add and get data`() = runBlockingTest {
        val peoples = List<People>(5){ People("이름${it}",it) }
        for(i in 0 until 5){
            addUsecase(peoples[i])
        }
        Assert.assertEquals(peoples, getAllUsecase())
    }
}