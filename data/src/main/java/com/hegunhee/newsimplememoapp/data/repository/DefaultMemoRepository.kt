package com.hegunhee.newsimplememoapp.data.repository

import com.hegunhee.newsimplememoapp.data.dao.MemoDao
import com.hegunhee.newsimplememoapp.data.entity.toMemoEntity
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultMemoRepository @Inject constructor(
    private val dao : MemoDao
) : MemoRepository {
    override suspend fun insertMemo(memo: MemoType.Memo) {
        dao.insertMemo(memo.toMemoEntity())
    }

    override suspend fun getMemo(memoId: Int) : MemoType.Memo {
        return dao.getMemo(memoId).toMemo()
    }

    override suspend fun deleteAllMemo() {
        dao.deleteAllMemo()
    }

    override suspend fun deleteMemo(memo: MemoType.Memo) {
        dao.deleteMemo(memo.toMemoEntity())
    }

    override suspend fun getMemoTypeListSortedByYearAndMonth(year: Int, month: Int): List<MemoType> {
        val group = dao.getMemoListSortedByYearAndMonth(year,month).groupBy { it.day }
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
        dao.updateMemo(memo.toMemoEntity())
    }
}