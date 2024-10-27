package com.hegunhee.newsimplememoapp.data.api

import com.hegunhee.newsimplememoapp.data.provideConverterFactory
import com.hegunhee.newsimplememoapp.data.provideJson
import com.hegunhee.newsimplememoapp.data.provideOkHttpClient
import org.junit.Assert.assertEquals

import org.junit.Before;

import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.Retrofit;

@ExperimentalCoroutinesApi
class MemoApiTest {

    private lateinit var server: MockWebServer
    private lateinit var memoApi: MemoApi
    private lateinit var retrofit: Retrofit

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()

        retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(provideJson().provideConverterFactory())
            .client(provideOkHttpClient())
            .build()

        memoApi = retrofit.create(MemoApi::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun givenMemoId_whenGetMemo_thenReturnMemo() = runTest {
        // Given
        val memoId = 1
        val response = """
            {
                "memoId": 1,
                "memoDate": "2024-08-23T09:48:00",
                "incomeExpenseType": "EXPENSE",
                "attribute": "식비",
                "asset": "현금",
                "description": "식비로 사용했습니다",
                "price": "10000.00"
            }
        """.trimIndent()
        server.enqueue(MockResponse().setBody(response))

        // When
        val memo = memoApi.findOne(memoId)

        // Then
        assertNotNull(memo)
        assertEquals(memoId,memo.id)
    }

}