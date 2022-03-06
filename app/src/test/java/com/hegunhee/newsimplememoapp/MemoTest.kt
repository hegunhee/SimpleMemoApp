package com.hegunhee.newsimplememoapp

import android.app.Application
import com.hegunhee.newsimplememoapp.data.Memo
import com.hegunhee.newsimplememoapp.di.memoTestModule
import com.hegunhee.newsimplememoapp.viewmodel.MemoTestViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
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
        startKoin {
            androidContext(context)
            modules(memoTestModule)
        }
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `add Memo`() = runBlockingTest{
        val memo = Memo(
            "수입", 2022, 3, 6, "일", "오후", 8, 6, "용돈", 10000, "설명"
        )
        val memos = listOf<Memo>(memo)
        viewModel.addMemo(memo)
        viewModel.getAllMemo()
        assertEquals(viewModel.memos,memos)
    }

    @Test
    fun `delete All Memo`() = runBlockingTest {
        val memo = Memo(
            "수입", 2022, 3, 6, "일", "오후", 8, 6, "용돈", 10000, "설명"
        )
        val memos = listOf<Memo>()
        viewModel.addMemo(memo)
        viewModel.deleteAllMemoUseCase()
        viewModel.getAllMemo()
        assertEquals(viewModel.memos,memos)
    }

    @Test
    fun `delete One Memo`() = runBlockingTest {
        val memo = Memo(
            "수입", 2022, 3, 6, "일", "오후", 8, 6, "용돈", 10000, "설명"
        )
        val memo2 = Memo(
            "수입1", 2022, 3, 6, "일", "오후", 8, 6, "용돈", 10000, "설명"
        )
        val memos = listOf<Memo>(memo,memo2)
        viewModel.addMemo(memo)
        viewModel.addMemo(memo2)
        viewModel.getAllMemo()
        viewModel.deleteMemo(memo2)
        val memos2 = listOf<Memo>(memo)
        viewModel.getAllMemo()
        assertEquals(viewModel.memos,memos2)
    }

}