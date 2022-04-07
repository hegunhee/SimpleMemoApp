package com.hegunhee.newsimplememoapp.domain.memoUsecase

import com.hegunhee.newsimplememoapp.domain.UseCase
import com.hegunhee.newsimplememoapp.model.MemoRepository
import com.hegunhee.newsimplememoapp.ui.statics.StaticsData

class GetStaticsDataUseCase(
    private val repository : MemoRepository
) : UseCase {
    suspend operator fun invoke(category : String,year : Int, month: Int) : List<StaticsData>{
        val staticsData = mutableListOf<StaticsData>()
        val data = repository.getMemoListSortedByCategoryAndYearAndMonth(category,year,month)
        val totalValue = data.sumOf { it.price }
        data.groupBy { it.attr }.forEach{ attr,list ->
            val sum = list.sumOf { it.price }.toDouble()
            staticsData.add(StaticsData(((sum/totalValue)*100).toInt(),attr,sum.toInt(),year,month))
        }
        return staticsData.sortedByDescending { it.percent }.toList()
    }
}