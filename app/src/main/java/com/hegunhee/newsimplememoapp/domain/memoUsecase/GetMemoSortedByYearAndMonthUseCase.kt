package com.hegunhee.newsimplememoapp.domain.memoUsecase

import com.hegunhee.newsimplememoapp.data.entity.Memo
import com.hegunhee.newsimplememoapp.model.MemoRepository
import javax.inject.Inject

class GetMemoSortedByYearAndMonthUseCase @Inject constructor(private val repository: MemoRepository) {

    suspend operator fun invoke(year: Int, month: Int): List<Memo> {
        return repository.getMemoListSortedByYearAndMonth(year, month)
            .sortedByDescending { it.minute }.sortedByDescending {
                it.hour + if (it.amPm == "오후") 12 else 0
            }.sortedByDescending { it.day }

    }
}