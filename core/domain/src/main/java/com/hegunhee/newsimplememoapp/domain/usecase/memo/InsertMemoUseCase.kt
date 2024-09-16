package com.hegunhee.newsimplememoapp.domain.usecase.memo

import com.hegunhee.newsimplememoapp.domain.model.memo.MemoForm
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class InsertMemoUseCase @Inject constructor(
    private val memoRepository : MemoRepository
) {

    suspend operator fun invoke(memoForm: MemoForm) : Result<Int>{
        return memoRepository.insertMemo(memoForm);
    }
}