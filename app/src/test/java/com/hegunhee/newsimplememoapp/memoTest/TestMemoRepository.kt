package com.hegunhee.newsimplememoapp.memoTest

import android.util.Log
import com.hegunhee.newsimplememoapp.data.Dao.MemoDao
import com.hegunhee.newsimplememoapp.data.Entity.Memo
import com.hegunhee.newsimplememoapp.model.MemoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TestMemoRepository() : MemoRepository {

    val memoList : MutableList<Memo> = mutableListOf()

    override suspend fun addMemo(memo: Memo) {
        memoList.add(memo)
    }

    override suspend fun getAllMemo(): List<Memo> {
        return memoList.toList()
    }

    override suspend fun deleteAllMemo() {
        memoList.clear()
    }

    override suspend fun deleteMemo(memo: Memo){
        memoList.remove(memo)
    }

    override suspend fun addMemoList(memos: List<Memo>) {
        memoList.addAll(memos)
    }
}