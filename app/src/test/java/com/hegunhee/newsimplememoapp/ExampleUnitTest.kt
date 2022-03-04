package com.hegunhee.newsimplememoapp

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var test_memo : TestMemo
    @Before
    fun inject_memo(){
        test_memo = TestMemo("수입",3000)
    }
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun `cute test code`(){
        val test = TestClass("Hello")
        assertEquals(TestClass("Hello"),test)
    }
    @Test
    fun `simple memo test`(){
        val memo = TestMemo("지출",10000)
        assertEquals(TestMemo.test_memo,memo)
    }
    @Test
    fun `test inject memo`(){
        val memo = TestMemo("수입",3000)
        assertEquals(test_memo,memo)
    }
}