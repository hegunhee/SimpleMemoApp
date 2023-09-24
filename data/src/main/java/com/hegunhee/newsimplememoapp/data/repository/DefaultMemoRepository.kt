package com.hegunhee.newsimplememoapp.data.repository

import com.hegunhee.newsimplememoapp.data.dataSource.LocalDataSource
import com.hegunhee.newsimplememoapp.data.entity.toMemoEntity
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
        val group = localDataSource.getMemoListSortedByYearAndMonth(year,month).groupBy { it.day }
        val memoTypeList = mutableListOf<MemoType>()
        group.forEach { (day, list) ->
            val incomeSum = list.filter { it.category == "수입" }.map { it.price }.sum()
            val expensesSum = list.filter { it.category == "지출" }.map { it.price }.sum()
            val memoDate : MemoType = MemoType.MemoDate(year,month,day,list[0].dayOfWeek,incomeSum,expensesSum)
            memoTypeList.add(memoDate)
            memoTypeList.addAll(list.map { it.toMemo() })
        }
        return memoTypeList.toList()
    }

    override suspend fun updateMemo(memo: MemoType.Memo) {
        localDataSource.updateMemo(memo.toMemoEntity())
    }
}