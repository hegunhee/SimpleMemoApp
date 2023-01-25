package com.hegunhee.newsimplememoapp.model

import com.hegunhee.newsimplememoapp.data.entity.MemoEntity

interface MemoRepository {

    suspend fun insertMemo(memoEntity : MemoEntity)

    suspend fun getAllMemo() : List<MemoEntity>

    suspend fun deleteAllMemo()

    suspend fun deleteMemo(memoEntity : MemoEntity)

    suspend fun insertMemoList(memoEntity : List<MemoEntity>)

    suspend fun getMemoListSortedByYearAndMonth(year : Int, month : Int) : List<MemoEntity>

    suspend fun getMemoListSortedByCategoryAndYearAndMonth(category : String,year : Int,month : Int) : List<MemoEntity>

    suspend fun getMemoListSortedByAttrYearMonth(attr : String,year : Int,month : Int) : List<MemoEntity>
}
