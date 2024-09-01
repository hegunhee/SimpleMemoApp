package com.hegunhee.newsimplememoapp.domain.usecase.memo

import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import com.hegunhee.newsimplememoapp.domain.model.memo.StaticsMemos
import com.hegunhee.newsimplememoapp.domain.repository.MemoTempRepository
import javax.inject.Inject

class GetStaticsMemosUseCase @Inject constructor(
    private val memoTempRepository: MemoTempRepository
) {

    suspend operator fun invoke(incomeExpenseType: IncomeExpenseType,year : Int,month : Int) : Result<StaticsMemos> {
        return memoTempRepository.getStaticsMemo(incomeExpenseType,year,month)
    }
}