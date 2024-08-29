package com.hegunhee.newsimplememoapp.data.dataSource

import com.hegunhee.newsimplememoapp.data.api.dto.category.CategoryEntity
import com.hegunhee.newsimplememoapp.data.api.dto.category.dto.CategoryNameResponse
import com.hegunhee.newsimplememoapp.data.api.dto.category.dto.CategoryNamesResponse
import com.hegunhee.newsimplememoapp.data.api.dto.memo.MemoEntity
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.AttributeMemosResponse
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.MemoIdResponse
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.MemoRequestDto
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.MemoSummaryResponses
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.StaticsMemoResponses
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType


interface RemoteDataSource {

    suspend fun findMemo(memoId : Int) : MemoEntity

    suspend fun getMemosByDate(year : Int, month : Int) : MemoSummaryResponses

    suspend fun getMemosByAttr(attr : String, year : Int,month : Int) : AttributeMemosResponse

    suspend fun getStaticsMemos(type : IncomeExpenseType,year : Int, month : Int) : StaticsMemoResponses

    suspend fun insertMemo(memoRequest : MemoRequestDto) : MemoIdResponse

    suspend fun updateMemo(memoId : Int,memo : MemoRequestDto) : MemoIdResponse

    suspend fun deleteMemo(memoId : Int) : MemoIdResponse

    suspend fun findAllCategoryByType(categoryType : CategoryType) : CategoryNamesResponse

    suspend fun isExistCategory(categoryName : String) : Boolean

    suspend fun insertCategory(categoryEntity : CategoryEntity) : CategoryNameResponse

    suspend fun deleteCategory(categoryName : String) : CategoryNameResponse
}