package com.hegunhee.newsimplememoapp.domain.memoUsecase

import com.hegunhee.newsimplememoapp.data.entity.MemoEntity
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.model.MemoRepository
import javax.inject.Inject

class InsertMemoUseCase @Inject constructor(private val repository : MemoRepository) {

    suspend operator fun invoke(memo : MemoType.Memo){
        repository.insertMemo(memo)
    }
}