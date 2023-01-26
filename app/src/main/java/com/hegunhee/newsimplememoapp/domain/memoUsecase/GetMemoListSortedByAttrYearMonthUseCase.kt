package com.hegunhee.newsimplememoapp.domain.memoUsecase

import com.hegunhee.newsimplememoapp.data.entity.MemoEntity
import com.hegunhee.newsimplememoapp.model.MemoRepository
import javax.inject.Inject

class GetMemoListSortedByAttrYearMonthUseCase @Inject constructor(private val repository: MemoRepository) {

    suspend operator fun invoke(attr: String, year: Int, month: Int): List<MemoEntity> {
        return repository.getMemoListSortedByAttrYearMonth(attr, year, month)
    }
}