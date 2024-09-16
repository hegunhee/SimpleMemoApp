package com.hegunhee.newsimplememoapp.domain.usecase.memo

import com.hegunhee.newsimplememoapp.domain.model.memo.MemosSummary
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class GetMemosSummaryUseCase @Inject constructor(
    private val tempRepository: MemoRepository
) {

    suspend operator fun invoke(year : Int,month : Int) : Result<MemosSummary> {
        return tempRepository.getMemosByDate(year,month)
    }
}