package com.hegunhee.newsimplememoapp.data.repository

import com.hegunhee.newsimplememoapp.data.dataSource.LocalDataSource
import com.hegunhee.newsimplememoapp.data.entity.CategoryEntity
import com.hegunhee.newsimplememoapp.data.mapper.*
import com.hegunhee.newsimplememoapp.domain.model.CategoryType
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultMemoRepository @Inject constructor(
    private val localDataSource: LocalDataSource
) : MemoRepository {
    override suspend fun insertMemo(memo: MemoType.Memo) {
        localDataSource.insertMemo(memo.toMemoEntity())
    }

    override suspend fun getMemo(memoId: Int) : MemoType.Memo {
        return localDataSource.getMemo(memoId).toMemo()
    }

    override suspend fun deleteAllMemo() {
        localDataSource.deleteAllMemo()
    }

    override suspend fun deleteMemo(id : Int) {
        localDataSource.deleteMemo(id)
    }

    override suspend fun getMemoTypeListSortedByYearAndMonth(year: Int, month: Int): List<MemoType> {
        val memoList = localDataSource.getMemoListSortedByYearAndMonth(year,month)
        return memoList
            .groupBy { it.day }
            .flatMap { (day, list) ->
                val incomeSum = list.filter { it.category == "수입" }.sumBy { it.price }
                val expenseSum = list.filter { it.category == "지출"}.sumBy { it.price }
                val memoDate = MemoType.MemoDate(year,month,day,list.firstOrNull()?.dayOfWeek ?: "월", incomeSum,expenseSum)
                listOf(memoDate) + list.map { it.toMemo() }
            }
    }

    override suspend fun updateMemo(memo: MemoType.Memo) {
        localDataSource.updateMemo(memo.toMemoEntity())
    }

    override suspend fun getAllCategoryByType(categoryType: CategoryType): List<String> {
        return localDataSource.getAllCategoryByType(categoryType.code).map { it.text }
    }

    override suspend fun checkIsCategory(categoryType: CategoryType, text: String): Boolean {
        return localDataSource.checkIsCategory(categoryType.code,text) != null
    }

    override suspend fun deleteCategory(text: String) {
        localDataSource.deleteCategory(text)
    }

    override suspend fun insertCategory(categoryType: CategoryType, text: String) {
        localDataSource.insertCategory(CategoryEntity(categoryType.code,text))
    }
}