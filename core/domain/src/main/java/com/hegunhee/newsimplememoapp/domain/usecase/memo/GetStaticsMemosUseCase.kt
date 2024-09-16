package com.hegunhee.newsimplememoapp.domain.usecase.memo

import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import com.hegunhee.newsimplememoapp.domain.model.memo.StaticsMemos
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class GetStaticsMemosUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {

    suspend operator fun invoke(incomeExpenseType: IncomeExpenseType,year : Int,month : Int) : Result<StaticsMemos> {
        return memoRepository.getStaticsMemo(incomeExpenseType,year,month)
    }
}