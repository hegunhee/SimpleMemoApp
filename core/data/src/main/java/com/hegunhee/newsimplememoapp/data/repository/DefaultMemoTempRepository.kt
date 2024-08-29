package com.hegunhee.newsimplememoapp.data.repository

import com.hegunhee.newsimplememoapp.data.dataSource.RemoteDataSource
import com.hegunhee.newsimplememoapp.data.mapper.toAttributeMemos
import com.hegunhee.newsimplememoapp.data.mapper.toMemo
import com.hegunhee.newsimplememoapp.data.mapper.toMemoRequest
import com.hegunhee.newsimplememoapp.data.mapper.toMemosSummary
import com.hegunhee.newsimplememoapp.data.mapper.toStaticsMemos
import com.hegunhee.newsimplememoapp.domain.model.memo.AttributeMemos
import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import com.hegunhee.newsimplememoapp.domain.model.memo.MemoForm
import com.hegunhee.newsimplememoapp.domain.model.memo.MemoServer
import com.hegunhee.newsimplememoapp.domain.model.memo.MemosSummary
import com.hegunhee.newsimplememoapp.domain.model.memo.StaticsMemos
import com.hegunhee.newsimplememoapp.domain.repository.MemoTempRepository
import javax.inject.Inject

class DefaultMemoTempRepository @Inject constructor(
    private val remoteDataSource : RemoteDataSource
) : MemoTempRepository {
    override suspend fun getMemo(memoId: Int): Result<MemoServer> {
        return runCatching { remoteDataSource.findMemo(memoId).toMemo() }
    }

    override suspend fun getMemosByDate(year: Int, month: Int): Result<MemosSummary> {
        return runCatching { remoteDataSource.getMemosByDate(year,month).toMemosSummary() }
    }

    override suspend fun getMemosByAttr(
        attr: String,
        year: Int,
        month: Int
    ): Result<AttributeMemos> {
        return runCatching { remoteDataSource.getMemosByAttr(attr,year,month).toAttributeMemos() }
    }

    override suspend fun getStaticsMemo(
        type: IncomeExpenseType,
        year: Int,
        month: Int
    ): Result<StaticsMemos> {
        return runCatching { remoteDataSource.getStaticsMemos(type,year,month).toStaticsMemos() }
    }

    override suspend fun insertMemo(memoForm: MemoForm): Result<Int> {
        return runCatching { remoteDataSource.insertMemo(memoForm.toMemoRequest()).memoId }
    }

    override suspend fun updateMemo(memoId: Int, memoForm: MemoForm): Result<Int> {
        return runCatching { remoteDataSource.updateMemo(memoId,memoForm.toMemoRequest()).memoId }
    }

    override suspend fun deleteMemo(memoId: Int): Result<Int> {
        return runCatching { remoteDataSource.deleteMemo(memoId).memoId }
    }
}