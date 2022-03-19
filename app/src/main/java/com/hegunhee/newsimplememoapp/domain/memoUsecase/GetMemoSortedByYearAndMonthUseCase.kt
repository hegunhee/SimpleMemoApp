package com.hegunhee.newsimplememoapp.domain.memoUsecase

import com.hegunhee.newsimplememoapp.data.entity.Memo
import com.hegunhee.newsimplememoapp.domain.UseCase
import com.hegunhee.newsimplememoapp.model.MemoRepository

class GetMemoSortedByYearAndMonthUseCase(
    private val repository: MemoRepository
) : UseCase {
    suspend operator fun invoke(year : Int,month : Int) : List<Memo>{
        return repository.getMemoSortedByYearAndMonth(year,month)
    }
}