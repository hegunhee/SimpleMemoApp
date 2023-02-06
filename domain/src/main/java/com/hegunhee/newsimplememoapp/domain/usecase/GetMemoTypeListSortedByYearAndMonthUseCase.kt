package com.hegunhee.newsimplememoapp.domain.usecase

import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class GetMemoTypeListSortedByYearAndMonthUseCase @Inject constructor(private val repository: MemoRepository) {

    suspend operator fun invoke(year : Int,month : Int) : List<MemoType>{
        return repository.getMemoTypeListSortedByYearAndMonth(year,month)
    }
}