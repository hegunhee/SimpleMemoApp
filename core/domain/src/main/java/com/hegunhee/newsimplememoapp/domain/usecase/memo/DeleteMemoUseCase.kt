package com.hegunhee.newsimplememoapp.domain.usecase.memo

import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class DeleteMemoUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {

    suspend operator fun invoke(memoId: Int) : Result<Int> {
        return memoRepository.deleteMemo(memoId)
    }
}