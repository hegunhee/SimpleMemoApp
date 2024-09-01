package com.hegunhee.newsimplememoapp.domain.usecase.memo

import com.hegunhee.newsimplememoapp.domain.model.memo.AttributeMemos
import com.hegunhee.newsimplememoapp.domain.repository.MemoTempRepository
import javax.inject.Inject

class GetMemosByAttrUseCase @Inject constructor(
    private val memoTempRepository: MemoTempRepository
) {

    suspend operator fun invoke(attr : String,year : Int,month : Int) : Result<AttributeMemos> {
        return memoTempRepository.getMemosByAttr(attr,year,month)
    }
}