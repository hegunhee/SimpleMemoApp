package com.hegunhee.newsimplememoapp.domain.repository

import com.hegunhee.newsimplememoapp.domain.model.MemoType

interface MemoRepository {

    suspend fun insertMemo(memo : MemoType.Memo)

    suspend fun getMemo(memoId: Int) : MemoType.Memo

    suspend fun deleteAllMemo()

    suspend fun deleteMemo(memo : MemoType.Memo)

    suspend fun getMemoTypeListSortedByYearAndMonth(year : Int,month : Int) : List<MemoType>
    suspend fun updateMemo(memo : MemoType.Memo)
}