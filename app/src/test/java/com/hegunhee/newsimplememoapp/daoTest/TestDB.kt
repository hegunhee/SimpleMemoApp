package com.hegunhee.newsimplememoapp.daoTest

import android.app.Application
import androidx.room.Room
import com.hegunhee.newsimplememoapp.di.memoTestModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import kotlin.test.assertEquals

class TestDB : KoinTest{
    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var context: Application

    private lateinit var db : PersonDB

    private val dispatcher = TestCoroutineDispatcher()


    @Before
    fun setUp() {
        startKoin {
            androidContext(context)
            modules(memoTestModule)
        }
        db = Room.inMemoryDatabaseBuilder(context,PersonDB::class.java).build()
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        stopKoin()
        db.close()
        Dispatchers.resetMain()
    }


    @Test
    fun `dao test`(){
        val dao = db.dao()
        runBlocking {
            val person = Person(name = "hello")
            dao.insert(person)
            val dbPerson = dao.selectAll()
            assertEquals(dbPerson[0].name,person.name)
        }
    }
}