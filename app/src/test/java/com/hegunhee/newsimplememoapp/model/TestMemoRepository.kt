package com.hegunhee.newsimplememoapp.model

import android.util.Log
import com.hegunhee.newsimplememoapp.data.DB.MemoDatabase
import com.hegunhee.newsimplememoapp.data.Dao.MemoDao
import com.hegunhee.newsimplememoapp.data.Entity.Memo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.withContext

class TestMemoRepository(val dao : MemoDao,
val ioDispatcher: CoroutineDispatcher) : MemoRepository{

    override suspend fun addMemo(memo: Memo) = withContext(ioDispatcher) {
        Log.d("Test","addMemo")
        dao.addMemo(memo)
    }

    override suspend fun getAllMemo(): List<Memo> = withContext(ioDispatcher) {
        Log.d("Test","getAllMemo")
        return@withContext dao.getAllMemo()
    }

    override suspend fun deleteAllMemo() {
    }

    override suspend fun deleteMemo(memo: Memo) = withContext(ioDispatcher) {
        dao.deleteMemo(memo)
    }
}