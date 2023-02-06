package com.hegunhee.newsimplememoapp.domain.usecase

import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class InsertMemoUseCase @Inject constructor(private val repository : MemoRepository) {

    suspend operator fun invoke(memo : MemoType.Memo){
        repository.insertMemo(memo)
    }
}