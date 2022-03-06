package com.hegunhee.newsimplememoapp.model

import com.hegunhee.newsimplememoapp.data.Memo

class TestMemoRepository : MemoRepository{

    val memoDB = mutableListOf<Memo>()
    override suspend fun addMemo(memo: Memo) {
        memoDB.add(memo)
    }

    override suspend fun getAllMemo(): List<Memo> {
        return memoDB.toList()
    }

    override suspend fun deleteAllMemo() {
        memoDB.clear()
    }

    override suspend fun deleteMemo(memo: Memo) {
        memoDB.remove(memo)
    }
}