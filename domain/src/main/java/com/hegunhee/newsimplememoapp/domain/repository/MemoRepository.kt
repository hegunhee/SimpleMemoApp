package com.hegunhee.newsimplememoapp.domain.repository

import com.hegunhee.newsimplememoapp.domain.model.CategoryType
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.model.StaticsData

interface MemoRepository {

    suspend fun insertMemo(memo : MemoType.Memo)

    suspend fun getMemo(memoId: Int) : MemoType.Memo

    suspend fun deleteAllMemo()

    suspend fun deleteMemo(id : Int)

    suspend fun getMemoTypeListSortedByYearAndMonth(year : Int,month : Int) : List<MemoType>

    suspend fun updateMemo(memo : MemoType.Memo)

    suspend fun getAllCategoryByType(categoryType : CategoryType) : List<String>

    suspend fun checkIsCategory(categoryType: CategoryType,text : String) : Boolean

    suspend fun deleteCategory(text : String)

    suspend fun insertCategory(categoryType: CategoryType, text : String)

    suspend fun getStaticsData(year : Int,month :Int) : List<StaticsData>
}
