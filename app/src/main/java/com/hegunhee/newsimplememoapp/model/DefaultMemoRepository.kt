package com.hegunhee.newsimplememoapp.model

import com.hegunhee.newsimplememoapp.data.Entity.Memo

class DefaultMemoRepository : MemoRepository {
    override suspend fun addMemo(memo: Memo) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllMemo(): List<Memo> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllMemo() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMemo(memo: Memo) {
        TODO("Not yet implemented")
    }

    override suspend fun addMemoList(memo: List<Memo>) {
        TODO("Not yet implemented")
    }

    override suspend fun getMemoSortedByYearAndMonth(year: Int, month: Int): List<Memo> {
        TODO("Not yet implemented")
    }


}