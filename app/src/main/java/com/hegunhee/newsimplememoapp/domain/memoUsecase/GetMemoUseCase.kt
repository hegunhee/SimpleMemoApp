package com.hegunhee.newsimplememoapp.domain.memoUsecase

import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.model.MemoRepository
import javax.inject.Inject

class GetMemoUseCase @Inject constructor(private val repository: MemoRepository) {

    suspend operator fun invoke(memoId : Int) : MemoType.Memo{
        return repository.getMemo(memoId)
    }


}