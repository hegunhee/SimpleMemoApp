package com.hegunhee.newsimplememoapp.data.repository

import com.hegunhee.newsimplememoapp.data.api.dto.memo.MemoEntity
import com.hegunhee.newsimplememoapp.data.dataSource.RemoteDataSource
import com.hegunhee.newsimplememoapp.data.mapper.toMemo
import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MemoRepositoryTest {

    @InjectMocks
    private lateinit var sut : DefaultMemoRepository

    @Mock
    private lateinit var remoteDataSource : RemoteDataSource

    @Test
    fun giveMemoId_whenGetMemo_thenReturnsMemo() = runTest {
        // Given
        val memoId = 1
        val expectedMemo = MemoEntity(memoId, LocalDateTime.now(),IncomeExpenseType.INCOME,"","","", BigDecimal.ZERO)
        whenever(remoteDataSource.findMemo(memoId)).thenReturn(expectedMemo)

        // When
        val result = sut.getMemo(memoId).getOrThrow()

        // Then
        assertEquals(expectedMemo.toMemo(),result)
        assertEquals(memoId,result.id)
        verify(remoteDataSource).findMemo(memoId)
    }
}
