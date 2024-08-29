package com.hegunhee.newsimplememoapp.domain.repository

import com.hegunhee.newsimplememoapp.domain.model.memo.AttributeMemos
import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import com.hegunhee.newsimplememoapp.domain.model.memo.MemoForm
import com.hegunhee.newsimplememoapp.domain.model.memo.MemoServer
import com.hegunhee.newsimplememoapp.domain.model.memo.MemosSummary
import com.hegunhee.newsimplememoapp.domain.model.memo.StaticsMemos

interface MemoTempRepository {

    suspend fun getMemo(memoId : Int) : Result<MemoServer>

    suspend fun getMemosByDate(year : Int, month : Int) : Result<MemosSummary>

    suspend fun getMemosByAttr(attr : String,year :Int, month : Int) : Result<AttributeMemos>

    suspend fun getStaticsMemo(type : IncomeExpenseType,year : Int,month : Int) : Result<StaticsMemos>

    suspend fun insertMemo(memoForm : MemoForm) : Result<Int>

    suspend fun updateMemo(memoId : Int,memoForm: MemoForm) : Result<Int>

    suspend fun deleteMemo(memoId : Int) : Result<Int>
}