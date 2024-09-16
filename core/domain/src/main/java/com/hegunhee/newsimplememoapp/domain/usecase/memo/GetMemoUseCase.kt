package com.hegunhee.newsimplememoapp.domain.usecase.memo

import com.hegunhee.newsimplememoapp.domain.model.memo.Memo
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class GetMemoUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {

    suspend operator fun invoke(memoId : Int) : Result<Memo> {
        return memoRepository.getMemo(memoId)
    }
}