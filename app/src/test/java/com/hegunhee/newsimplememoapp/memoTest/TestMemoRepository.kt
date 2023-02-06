package com.hegunhee.newsimplememoapp.memoTest

import com.hegunhee.newsimplememoapp.data.entity.MemoEntity

class TestMemoRepository() : MemoRepository {

    val memoEntityList : MutableList<MemoEntity> = mutableListOf()

    override suspend fun insertMemo(memoEntity: MemoEntity) {
        memoEntityList.add(memoEntity)
    }

    override suspend fun getAllMemo(): List<MemoEntity> {
        return memoEntityList.toList()
    }

    override suspend fun deleteAllMemo() {
        memoEntityList.clear()
    }

    override suspend fun deleteMemo(memoEntity: MemoEntity){
        memoEntityList.remove(memoEntity)
    }

    override suspend fun insertMemoList(memoEntities: List<MemoEntity>) {
        memoEntityList.addAll(memoEntities)
    }

    override suspend fun getMemoListSortedByYearAndMonth(year: Int, month: Int): List<MemoEntity> {
        return memoEntityList.filter { it.year == year }.filter { it.month == month }.sortedByDescending { it.day }.toList()
    }


}