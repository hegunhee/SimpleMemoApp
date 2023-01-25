package com.hegunhee.newsimplememoapp.model

import com.hegunhee.newsimplememoapp.data.entity.MemoEntity
import com.hegunhee.newsimplememoapp.domain.model.MemoType

interface MemoRepository {

    suspend fun insertMemo(memoEntity : MemoEntity)

    suspend fun getMemo(memoId: Int) : MemoType.Memo

    suspend fun getAllMemo() : List<MemoEntity>

    suspend fun deleteAllMemo()

    suspend fun deleteMemo(memo : MemoType.Memo)

    suspend fun insertMemoList(memoEntity : List<MemoEntity>)

    suspend fun getMemoListSortedByYearAndMonth(year : Int, month : Int) : List<MemoEntity>

    suspend fun getMemoTypeListSortedByYearAndMonth(year : Int,month : Int) : List<MemoType>

    suspend fun getMemoListSortedByCategoryAndYearAndMonth(category : String,year : Int,month : Int) : List<MemoEntity>

    suspend fun getMemoListSortedByAttrYearMonth(attr : String,year : Int,month : Int) : List<MemoEntity>
}
