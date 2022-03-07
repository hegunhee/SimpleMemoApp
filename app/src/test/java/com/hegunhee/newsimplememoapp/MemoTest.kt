package com.hegunhee.newsimplememoapp

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hegunhee.newsimplememoapp.data.DB.MemoDatabase
import com.hegunhee.newsimplememoapp.data.Dao.MemoDao
import com.hegunhee.newsimplememoapp.data.Entity.Memo
import com.hegunhee.newsimplememoapp.data.toTestMemo
import com.hegunhee.newsimplememoapp.di.memoTestModule
import com.hegunhee.newsimplememoapp.viewmodel.MemoTestViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
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
//    private lateinit var db : MemoDatabase
//    private lateinit var dao : MemoDao

    @Before
    fun setUp() {
        startKoin {
            androidContext(context)
            modules(memoTestModule)
        }
//        db = Room.inMemoryDatabaseBuilder(context,MemoDatabase::class.java).build()
//        dao = db.dao()
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        stopKoin()
//        db.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `add Memo`() = runBlockingTest{
        val memo = Memo(
            1, "수입", 2022, 3, 6, "일", "오후", 8, 6, "용돈", 10000, "설명"
        )
        val memos = listOf<Memo>(memo)
        viewModel.addMemo(memo)
        viewModel.getAllMemo()
        assertEquals(memo,memos[0])
    }

//    @Test
//    fun `db test`() = runBlocking{
//        val memo = Memo(
//            1, "수입", 2022, 3, 6, "일", "오후", 8, 6, "용돈", 10000, "설명"
//        )
//        val memos = listOf<Memo>(memo)
//        dao.addMemo(memo)
//        val daomemos = dao.getAllMemo()
//        assertEquals(daomemos,memos)
//
//    }

//    @Test
//    fun `delete All Memo`() = runBlockingTest {
//        val memo = Memo(
//            1, "수입", 2022, 3, 6, "일", "오후", 8, 6, "용돈", 10000, "설명"
//        )
//        val memos = listOf<Memo>()
//        viewModel.addMemo(memo)
//        viewModel.deleteMemo(memo)
//        viewModel.getAllMemo()
//        assertEquals(toTestMemo(viewModel.memos[0]), memos)
//    }
//
//    @Test
//    fun `delete One Memo`() = runBlockingTest {
//        val memo = Memo(
//            1, "수입", 2022, 3, 6, "일", "오후", 8, 6, "용돈", 10000, "설명"
//        )
//        val memo2 = Memo(
//            2, "수입1", 2022, 3, 6, "일", "오후", 8, 6, "용돈", 10000, "설명"
//        )
//        val memos = listOf<Memo>(memo, memo2)
//        viewModel.addMemo(memo)
//        viewModel.addMemo(memo2)
//        viewModel.getAllMemo()
//        viewModel.deleteMemo(memo2)
//        val memos2 = listOf<Memo>(memo)
//        viewModel.getAllMemo()
//        assertEquals(viewModel.memos, memos2)
//    }

}