package com.hegunhee.newsimplememoapp.domain.usecase.memo

import com.hegunhee.newsimplememoapp.domain.model.memo.MemoServer
import com.hegunhee.newsimplememoapp.domain.repository.MemoTempRepository
import javax.inject.Inject

class GetMemoServerUseCase @Inject constructor(
    private val memoTempRepository: MemoTempRepository
) {

    suspend operator fun invoke(memoId : Int) : Result<MemoServer> {
        return memoTempRepository.getMemo(memoId)
    }
}