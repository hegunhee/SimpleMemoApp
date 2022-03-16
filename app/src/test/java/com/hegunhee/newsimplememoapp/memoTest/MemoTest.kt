package com.hegunhee.newsimplememoapp.memoTest

import android.app.Application
import com.hegunhee.newsimplememoapp.data.Entity.Memo
import com.hegunhee.newsimplememoapp.data.Entity.getOneMockingMemo
import com.hegunhee.newsimplememoapp.data.Entity.getTwentyMockingMemo
import com.hegunhee.newsimplememoapp.di.memoTestModule
import com.hegunhee.newsimplememoapp.memoTest.MemoTestViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import kotlin.test.assertEquals

open class MemoTest : KoinTest {

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var context: Application


    private val dispatcher = TestCoroutineDispatcher()

    val viewModel: MemoTestViewModel by inject()

    @Before
    fun setUp() {
//        startKoin {
//            androidContext(context)
//            modules(memoTestModule)
//        }
//        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `add Memo`() = runBlockingTest {
        val memo = getOneMockingMemo()
        val memos = listOf<Memo>(memo)
        viewModel.addMemo(memo)
        viewModel.getAllMemo()
        assertEquals(viewModel.memos, memos)
    }

    @Test
    fun `delete One Memo`() = runBlockingTest {
        val memo = getOneMockingMemo()
        val memos = listOf<Memo>()
        viewModel.addMemo(memo)
        viewModel.deleteMemo(memo)
        viewModel.getAllMemo()
        assertEquals(viewModel.memos, memos)
    }

    @Test
    fun `delete other Memo`() = runBlockingTest {
        val memos = getTwentyMockingMemo().filter { it.year == 2022}.filter { it.month == 3 }.toList()
        viewModel.addMemos(getTwentyMockingMemo())
        viewModel.getAllMemo()
        assertEquals(viewModel.memos.filter { it.year == 2022 }.filter{it.month == 3 }.toList(), memos)
    }

    @Test
    fun `delete all Memo`() = runBlockingTest {
        val memos = listOf<Memo>()
        viewModel.addMemos(getTwentyMockingMemo())
        viewModel.deleteAllMemo()
        viewModel.getAllMemo()
        assertEquals(viewModel.memos,memos)
    }

    @Test
    fun `delete other Memo by Repository`() = runBlockingTest {
        val memos = getTwentyMockingMemo().filter { it.year == 2022}.filter { it.month == 3 }.sortedByDescending { it.day }.toList()
        viewModel.addMemos(getTwentyMockingMemo())
        viewModel.getMemoSortedByYearAndMonth(2022,3)
        assertEquals(viewModel.memos, memos)
    }

}