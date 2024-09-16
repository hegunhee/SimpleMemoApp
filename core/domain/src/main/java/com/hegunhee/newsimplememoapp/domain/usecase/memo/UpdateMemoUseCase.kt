package com.hegunhee.newsimplememoapp.domain.usecase.memo

import com.hegunhee.newsimplememoapp.domain.model.memo.MemoForm
import com.hegunhee.newsimplememoapp.domain.repository.MemoTempRepository
import javax.inject.Inject

class UpdateMemoUseCase @Inject constructor(
    private val memoTempRepository: MemoTempRepository
) {

    suspend operator fun invoke(memoId : Int,memoForm : MemoForm) : Result<Int>{
        return memoTempRepository.updateMemo(memoId,memoForm)
    }
}