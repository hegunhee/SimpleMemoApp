package com.hegunhee.newsimplememoapp.domain.usecase.memo

import com.hegunhee.newsimplememoapp.domain.model.memo.AttributeMemos
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class GetMemosByAttrUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {

    suspend operator fun invoke(attr : String,year : Int,month : Int) : Result<AttributeMemos> {
        return memoRepository.getMemosByAttr(attr,year,month)
    }
}