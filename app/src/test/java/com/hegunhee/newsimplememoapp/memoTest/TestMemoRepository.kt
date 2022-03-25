package com.hegunhee.newsimplememoapp.memoTest

import com.hegunhee.newsimplememoapp.data.entity.Memo
import com.hegunhee.newsimplememoapp.model.MemoRepository

class TestMemoRepository() : MemoRepository {

    val memoList : MutableList<Memo> = mutableListOf()

    override suspend fun insertMemo(memo: Memo) {
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

    override suspend fun insertMemoList(memos: List<Memo>) {
        memoList.addAll(memos)
    }

    override suspend fun getMemoSortedByYearAndMonth(year: Int, month: Int): List<Memo> {
        return memoList.filter { it.year == year }.filter { it.month == month }.sortedByDescending { it.day }.toList()
    }


}