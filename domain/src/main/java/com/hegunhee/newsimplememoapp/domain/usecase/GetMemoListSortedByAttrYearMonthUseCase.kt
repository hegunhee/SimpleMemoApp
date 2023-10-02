package com.hegunhee.newsimplememoapp.domain.usecase

import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class GetMemoListSortedByAttrYearMonthUseCase @Inject constructor(private val repository: MemoRepository) {

    suspend operator fun invoke(attr: String, year: Int, month: Int): List<MemoType> {
        return repository.getMemoTypeListByAttr(attr,year,month)
    }
}