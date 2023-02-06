package com.hegunhee.newsimplememoapp.domain.usecase

import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class GetStaticsDataUseCase @Inject constructor(private val repository : MemoRepository) {
    suspend operator fun invoke(category : String,year : Int, month: Int) : List<Any>{
        /**
         * 현재 사용 안하는 UseCase, 리팩토링 예정
         */
        return emptyList()
//        val staticsData = mutableListOf<StaticsData>()
//        val data = repository.getMemoListSortedByCategoryAndYearAndMonth(category,year,month)
//        val totalValue = data.sumOf { it.price }
//        data.groupBy { it.attr }.forEach{ attr,list ->
//            val sum = list.sumOf { it.price }.toDouble()
//            staticsData.add(StaticsData(((sum/totalValue)*100).toInt(),attr,sum.toInt(),year,month))
//        }
//        return staticsData.sortedByDescending { it.percent }.toList()
    }
}