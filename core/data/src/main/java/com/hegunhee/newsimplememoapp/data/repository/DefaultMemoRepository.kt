package com.hegunhee.newsimplememoapp.data.repository

import com.hegunhee.newsimplememoapp.data.dataSource.LocalDataSource
import com.hegunhee.newsimplememoapp.data.entity.MemoEntity
import com.hegunhee.newsimplememoapp.data.mapper.*
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.model.StaticsData
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultMemoRepository @Inject constructor(
    private val localDataSource: LocalDataSource
) : MemoRepository {

    override suspend fun getMemo(memoId: Int) : MemoType.Memo {
        return localDataSource.getMemo(memoId).toMemo()
    }

    override suspend fun getMemoTypeListSortedByYearAndMonth(year: Int, month: Int): List<MemoType> {
        val memoList = localDataSource.getMemoListSortedByYearAndMonth(year,month)
        return memoList.getMonthMemoList(year,month);
    }

    override suspend fun getMemoTypeListByAttr(attr: String, year: Int, month: Int): List<MemoType> {
        val memoList = localDataSource.getMemoListSortedByAttrYearMonth(attr,year,month)
        return memoList.getMonthMemoList(year,month);
    }

    private fun List<MemoEntity>.getMonthMemoList(year : Int,month : Int) : List<MemoType> {
        return this
            .groupBy { it.day }
            .flatMap { (day, list) ->
                val incomeSum = list.filter { it.category == "수입" }.sumOf { it.price }
                val expenseSum = list.filter { it.category == "지출"}.sumOf { it.price }
                val memoDate = MemoType.MemoDate(year,month,day,list.firstOrNull()?.dayOfWeek ?: "월", incomeSum,expenseSum)
                listOf(memoDate) + list.map { it.toMemo() }
            }
    }

    override suspend fun insertMemo(memo: MemoType.Memo) {
        localDataSource.insertMemo(memo.toMemoEntity())
    }

    override suspend fun updateMemo(memo: MemoType.Memo) {
        localDataSource.updateMemo(memo.toMemoEntity())
    }

    override suspend fun deleteMemo(id : Int) {
        localDataSource.deleteMemo(id)
    }

    override suspend fun deleteAllMemo() {
        localDataSource.deleteAllMemo()
    }

    override suspend fun getStaticsData(year: Int, month: Int): List<StaticsData> {
        val memoList = localDataSource.getMemoListSortedByYearAndMonth(year,month)
        val incomeTotalPrice = memoList.filter { it.category == "수입" }.sumOf { it.price }
        val expenseTotalPrice = memoList.filter { it.category == "지출" }.sumOf { it.price }
        return memoList.groupBy { it.attr }.map { (attr,list) ->
            val percent = if(list[0].category == "수입") {
                list.sumOf { it.price } / incomeTotalPrice.toFloat() * 100
            }else{
                list.sumOf { it.price } / expenseTotalPrice.toFloat() * 100
            }
            StaticsData(list[0].category,percent.toInt(),attr,list.sumOf { it.price },year,month)
        }
    }
}