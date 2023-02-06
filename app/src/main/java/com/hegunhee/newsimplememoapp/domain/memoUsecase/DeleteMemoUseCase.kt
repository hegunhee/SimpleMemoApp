package com.hegunhee.newsimplememoapp.domain.memoUsecase

import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.model.MemoRepository
import javax.inject.Inject

class DeleteMemoUseCase @Inject constructor(private val repository: MemoRepository) {
    suspend operator fun invoke(memo : MemoType.Memo){
        repository.deleteMemo(memo)
    }
}