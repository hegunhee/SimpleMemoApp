package com.hegunhee.newsimplememoapp.data

import com.hegunhee.newsimplememoapp.data.api.MemoApi
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.MemoRequestDto
import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDateTime

class MemoApiTest {

    private val memoApi: MemoApi = provideMemoApi()

    @Test
    fun `save memo`() {
        runBlocking {
            val memoRequestDto = MemoRequestDto(LocalDateTime.of(2024,8,27,18,8), IncomeExpenseType.INCOME,"분류", "자산", "설명설명", BigDecimal.valueOf(10000))
            var id = 0
            runCatching {
                val savedId = memoApi.save(memoRequestDto).memoId
                id = savedId
                memoApi.findOne(savedId)
            }.onSuccess { result ->
                Assert.assertEquals(memoRequestDto.asset,result.asset)
                memoApi.delete(id)
            }.onFailure {
                println("exception = "+it.message)
                assert(false)
            }
        }
    }

    @Test
    fun `memos test`() {
        runBlocking {
            val memoRequestDto1 = MemoRequestDto(LocalDateTime.of(2024,9,27,18,8), IncomeExpenseType.INCOME,"분류", "자산", "설명설명", BigDecimal.valueOf(10000))
            val memoRequestDto2 = MemoRequestDto(LocalDateTime.of(2024,8,27,18,8), IncomeExpenseType.EXPENSE,"분노", "자산", "설명설명", BigDecimal.valueOf(10000))
            val memoRequestDto3 = MemoRequestDto(LocalDateTime.of(2024,9,27,18,8), IncomeExpenseType.EXPENSE,"분류", "자산", "설명설명", BigDecimal.valueOf(10000))

            val memos = listOf(memoRequestDto1, memoRequestDto2, memoRequestDto3)
            runCatching {
                memos.forEach { memoApi.save(it) }
                memoApi.findMemos(2024,9)
            }.onSuccess { result ->
                Assert.assertEquals(2,result.memos.memos.size)
                Assert.assertEquals(0,result.totalSumResponse.totalSum.intValueExact())
            }.onFailure {
                println("exception = "+it.message)
                assert(false)
            }
        }
    }

    @Test
    fun `attribute memo test`() {
        runBlocking {
            val memoRequestDto1 = MemoRequestDto(LocalDateTime.of(2024,8,27,18,8), IncomeExpenseType.INCOME,"분류", "자산", "설명설명", BigDecimal.valueOf(10000))
            val memoRequestDto2 = MemoRequestDto(LocalDateTime.of(2024,8,27,18,8), IncomeExpenseType.INCOME,"분노", "자산", "설명설명", BigDecimal.valueOf(10000))
            val memoRequestDto3 = MemoRequestDto(LocalDateTime.of(2024,8,27,18,8), IncomeExpenseType.INCOME,"분류", "자산", "설명설명", BigDecimal.valueOf(20000))

            val memos = listOf(memoRequestDto1, memoRequestDto2, memoRequestDto3)
            runCatching {
                memos.forEach { memoApi.save(it) }
                memoApi.findMemosByAttribute("분류",2024,8)
            }.onSuccess { result ->
                Assert.assertEquals(2,result.memos.memos.size)
                Assert.assertEquals("분류",result.attribute)
                Assert.assertEquals(30000,result.price.intValueExact())
            }.onFailure {
                println("exception = "+it.message)
                assert(false)
            }
        }
    }

    @Test
    fun `statics memo test`() {
        runBlocking {
            val memoRequestDto1 = MemoRequestDto(LocalDateTime.of(2024,8,27,18,8), IncomeExpenseType.INCOME,"분류", "자산", "설명설명", BigDecimal.valueOf(10000))
            val memoRequestDto2 = MemoRequestDto(LocalDateTime.of(2024,8,27,18,8), IncomeExpenseType.EXPENSE,"분노", "자산", "설명설명", BigDecimal.valueOf(10000))
            val memoRequestDto3 = MemoRequestDto(LocalDateTime.of(2024,8,27,18,8), IncomeExpenseType.INCOME,"이류", "자산", "설명설명", BigDecimal.valueOf(20000))

            val memos = listOf(memoRequestDto1, memoRequestDto2, memoRequestDto3)
            runCatching {
                memos.forEach { memoApi.save(it) }
                memoApi.findMemosByIncomeExpenseType(IncomeExpenseType.INCOME,2024,8)
            }.onSuccess { result ->
                println(result)
                Assert.assertEquals(2,result.staticsMemos.size)
                Assert.assertEquals(IncomeExpenseType.INCOME,result.type)
                Assert.assertEquals(30000,result.totalPrice.intValueExact())
            }.onFailure {
                println("exception = "+it.message)
                assert(false)
            }
        }
    }

    @Test
    fun `delete memo`() {
        runBlocking {
            val memoRequestDto = MemoRequestDto(LocalDateTime.of(2024,8,27,18,8), IncomeExpenseType.INCOME,"분류", "자산", "설명설명", BigDecimal.valueOf(10000))
            var id = 0
            runCatching {
                val savedId = memoApi.save(memoRequestDto).memoId
                id = savedId
                memoApi.delete(savedId)
            }.onSuccess { result ->
                Assert.assertEquals(id,result.memoId)
            }.onFailure {
                println("exception = "+it.message)
                assert(false)
            }
        }
    }

    @Test
    fun `update memo`() {
        runBlocking {
            val memoRequestDto = MemoRequestDto(LocalDateTime.of(2024,8,27,18,8), IncomeExpenseType.INCOME,"분류", "자산", "설명설명", BigDecimal.valueOf(10000))
            var id = 0
            runCatching {
                val savedId = memoApi.save(memoRequestDto).memoId
                id = savedId
                val copiedMemo = memoRequestDto.copy(incomeExpenseType = IncomeExpenseType.EXPENSE)
                memoApi.update(id,copiedMemo)
            }.onSuccess { result ->
                Assert.assertEquals(id,result.memoId)
                memoApi.delete(id)
            }.onFailure {
                println("exception = "+it.message)
                assert(false)
            }
        }
    }
}