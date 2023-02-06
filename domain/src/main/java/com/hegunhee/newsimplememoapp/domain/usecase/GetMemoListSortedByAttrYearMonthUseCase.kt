package com.hegunhee.newsimplememoapp.domain.usecase

import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class GetMemoListSortedByAttrYearMonthUseCase @Inject constructor(private val repository: MemoRepository) {

    suspend operator fun invoke(attr: String, year: Int, month: Int): List<MemoType> {
        /**
         * 현재 사용 안하는 UseCase, 추 후에 리팩토링 예정
         */
        return emptyList()
    }
}