package com.hegunhee.newsimplememoapp.data.dataSource

import com.hegunhee.newsimplememoapp.data.api.CategoryApi
import com.hegunhee.newsimplememoapp.data.api.MemoApi
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
import javax.inject.Inject

class DefaultRemoteDataSource @Inject constructor(
    private val memoApi: MemoApi,
    private val categoryApi : CategoryApi
) : RemoteDataSource {
    override suspend fun findMemo(memoId: Int): MemoEntity {
        return memoApi.findOne(memoId)
    }

    override suspend fun getMemosByDate(year: Int, month: Int): MemoSummaryResponses {
        return memoApi.findMemos(year,month)
    }

    override suspend fun getMemosByAttr(
        attr: String,
        year: Int,
        month: Int
    ): AttributeMemosResponse {
        return memoApi.findMemosByAttribute(attr,year,month)
    }

    override suspend fun getStaticsMemos(
        type: IncomeExpenseType,
        year: Int,
        month: Int
    ): StaticsMemoResponses {
        return memoApi.findMemosByIncomeExpenseType(type,year,month)
    }

    override suspend fun insertMemo(memoRequest: MemoRequestDto): MemoIdResponse {
        return memoApi.save(memoRequest)
    }

    override suspend fun updateMemo(memoId: Int, memoRequest: MemoRequestDto): MemoIdResponse {
        return memoApi.update(memoId,memoRequest)
    }

    override suspend fun deleteMemo(memoId: Int): MemoIdResponse {
        return memoApi.delete(memoId)
    }

    override suspend fun findAllCategoryByType(categoryType: CategoryType): CategoryNamesResponse {
        return categoryApi.findAllBy(categoryType)
    }

    override suspend fun isExistCategory(categoryName: String): Boolean {
        return categoryApi.existsBy(categoryName)
    }

    override suspend fun insertCategory(categoryEntity: CategoryEntity): CategoryNameResponse {
        return categoryApi.save(categoryEntity)
    }

    override suspend fun deleteCategory(categoryName: String) : CategoryNameResponse{
        return categoryApi.delete(categoryName)
    }
}