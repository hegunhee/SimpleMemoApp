package com.hegunhee.newsimplememoapp.model

import com.hegunhee.newsimplememoapp.data.Dao.MemoDao
import com.hegunhee.newsimplememoapp.data.entity.Memo

class DefaultMemoRepository(
    private val dao : MemoDao
) : MemoRepository {
    override suspend fun insertMemo(memo: Memo) {
        dao.insertMemo(memo)
    }

    override suspend fun getAllMemo(): List<Memo> {
        return dao.getAllMemo()
    }

    override suspend fun deleteAllMemo() {
        dao.deleteAllMemo()
    }

    override suspend fun deleteMemo(memo: Memo) {
        dao.deleteMemo(memo)
    }

    override suspend fun insertMemoList(memo: List<Memo>) {
        dao.insertAllMemo(*memo.toTypedArray())
    }

    override suspend fun getMemoListSortedByYearAndMonth(year: Int, month: Int): List<Memo> {
        return dao.getMemoListSortedByYearAndMonth(year,month)
    }

    override suspend fun getMemoListSortedByCategoryAndYearAndMonth(
        category: String,
        year: Int,
        month: Int
    ): List<Memo> {
        return dao.getMemoListSortedByCategoryAndYearAndMonth(category,year,month)
    }

    override suspend fun getMemoListSortedByAttrYearMonth(
        attr: String,
        year: Int,
        month: Int
    ): List<Memo> {
        return dao.getMemoListSortedByAttrYearMonth(attr,year,month)
    }


}