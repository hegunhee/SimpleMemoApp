package com.hegunhee.newsimplememoapp.data.dataSource

import com.hegunhee.newsimplememoapp.data.dao.MemoDao
import com.hegunhee.newsimplememoapp.data.entity.MemoEntity
import javax.inject.Inject

class DefaultLocalDataSource @Inject constructor(
    private val memoDao : MemoDao
) : LocalDataSource{

    override suspend fun insertMemo(memo: MemoEntity) {
        memoDao.insertMemo(memo)
    }

    override suspend fun getMemo(memoId: Int): MemoEntity {
        return memoDao.getMemo(memoId)
    }

    override suspend fun deleteAllMemo() {
        memoDao.deleteAllMemo()
    }

    override suspend fun deleteMemo(id: Int) {
        memoDao.deleteMemo(id)
    }

    override suspend fun getMemoListSortedByYearAndMonth(year: Int, month: Int): List<MemoEntity> {
        return memoDao.getMemoListSortedByYearAndMonth(year,month)
    }

    override suspend fun updateMemo(memo: MemoEntity) {
        memoDao.updateMemo(memo)
    }


}