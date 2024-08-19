package com.hegunhee.newsimplememoapp.domain.repository

import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.model.StaticsData

interface MemoRepository {

    suspend fun getMemo(memoId: Int) : MemoType.Memo

    suspend fun getMemoTypeListSortedByYearAndMonth(year : Int,month : Int) : List<MemoType>

    suspend fun getMemoTypeListByAttr(attr : String, year : Int,month : Int) : List<MemoType>

    suspend fun insertMemo(memo : MemoType.Memo)

    suspend fun updateMemo(memo : MemoType.Memo)

    suspend fun deleteMemo(id : Int)

    suspend fun deleteAllMemo()

    suspend fun getStaticsData(year : Int,month :Int) : List<StaticsData>
}