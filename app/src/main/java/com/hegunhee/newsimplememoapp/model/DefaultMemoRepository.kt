package com.hegunhee.newsimplememoapp.model

import com.hegunhee.newsimplememoapp.data.Dao.MemoDao
import com.hegunhee.newsimplememoapp.data.entity.MemoEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultMemoRepository @Inject constructor(
    private val dao : MemoDao
) : MemoRepository {
    override suspend fun insertMemo(memoEntity: MemoEntity) {
        dao.insertMemo(memoEntity)
    }

    override suspend fun getAllMemo(): List<MemoEntity> {
        return dao.getAllMemo()
    }

    override suspend fun deleteAllMemo() {
        dao.deleteAllMemo()
    }

    override suspend fun deleteMemo(memoEntity: MemoEntity) {
        dao.deleteMemo(memoEntity)
    }

    override suspend fun insertMemoList(memoEntity: List<MemoEntity>) {
        dao.insertAllMemo(*memoEntity.toTypedArray())
    }

    override suspend fun getMemoListSortedByYearAndMonth(year: Int, month: Int): List<MemoEntity> {
        return dao.getMemoListSortedByYearAndMonth(year,month)
    }

    override suspend fun getMemoListSortedByCategoryAndYearAndMonth(
        category: String,
        year: Int,
        month: Int
    ): List<MemoEntity> {
        return dao.getMemoListSortedByCategoryAndYearAndMonth(category,year,month)
    }

    override suspend fun getMemoListSortedByAttrYearMonth(
        attr: String,
        year: Int,
        month: Int
    ): List<MemoEntity> {
        return dao.getMemoListSortedByAttrYearMonth(attr,year,month)
    }


}