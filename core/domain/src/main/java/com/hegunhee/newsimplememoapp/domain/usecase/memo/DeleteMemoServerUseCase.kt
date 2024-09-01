package com.hegunhee.newsimplememoapp.domain.usecase.memo

import com.hegunhee.newsimplememoapp.domain.repository.MemoTempRepository
import javax.inject.Inject

class DeleteMemoServerUseCase @Inject constructor(
    private val memoTempRepository: MemoTempRepository
) {

    suspend operator fun invoke(memoId: Int) : Result<Int> {
        return memoTempRepository.deleteMemo(memoId)
    }
}