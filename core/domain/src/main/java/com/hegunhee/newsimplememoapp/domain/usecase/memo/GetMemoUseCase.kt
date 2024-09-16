package com.hegunhee.newsimplememoapp.domain.usecase.memo

import com.hegunhee.newsimplememoapp.domain.model.memo.Memo
import com.hegunhee.newsimplememoapp.domain.repository.MemoTempRepository
import javax.inject.Inject

class GetMemoUseCase @Inject constructor(
    private val memoTempRepository: MemoTempRepository
) {

    suspend operator fun invoke(memoId : Int) : Result<Memo> {
        return memoTempRepository.getMemo(memoId)
    }
}