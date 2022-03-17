package com.hegunhee.newsimplememoapp.model

import com.hegunhee.newsimplememoapp.data.Dao.MemoDao
import com.hegunhee.newsimplememoapp.data.entity.Memo

class DefaultMemoRepository(
    private val dao : MemoDao
) : MemoRepository {
    override suspend fun addMemo(memo: Memo) {
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

    override suspend fun addMemoList(memo: List<Memo>) {
        dao.insertAllMemo(*memo.toTypedArray())
    }

    override suspend fun getMemoSortedByYearAndMonth(year: Int, month: Int): List<Memo> {
        return dao.getMemoSortedByYearAndMonth(year,month)
    }


}