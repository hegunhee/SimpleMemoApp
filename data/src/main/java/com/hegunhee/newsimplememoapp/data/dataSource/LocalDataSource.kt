package com.hegunhee.newsimplememoapp.data.dataSource

import com.hegunhee.newsimplememoapp.data.entity.CategoryEntity
import com.hegunhee.newsimplememoapp.data.entity.MemoEntity

interface LocalDataSource {

    suspend fun insertMemo(memo : MemoEntity)

    suspend fun getMemo(memoId : Int) : MemoEntity

    suspend fun deleteAllMemo()

    suspend fun deleteMemo(id : Int)

    suspend fun getMemoListSortedByYearAndMonth(year : Int, month : Int) : List<MemoEntity>

    suspend fun updateMemo(memo : MemoEntity)

    suspend fun getAllCategoryByType(categoryCode : Int) : List<CategoryEntity>

    suspend fun deleteCategory(text : String)

    suspend fun insertCategory(categoryEntity : CategoryEntity)
}