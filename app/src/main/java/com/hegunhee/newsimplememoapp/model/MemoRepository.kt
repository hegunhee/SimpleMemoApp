package com.hegunhee.newsimplememoapp.model

import com.hegunhee.newsimplememoapp.data.entity.Memo

interface MemoRepository {

    suspend fun addMemo(memo : Memo)

    suspend fun getAllMemo() : List<Memo>

    suspend fun deleteAllMemo()

    suspend fun deleteMemo(memo : Memo)

    suspend fun addMemoList(memo : List<Memo>)

    suspend fun getMemoSortedByYearAndMonth(year : Int, month : Int) : List<Memo>
}