package com.hegunhee.newsimplememoapp.domain.memoUsecase

import com.hegunhee.newsimplememoapp.data.entity.Memo
import com.hegunhee.newsimplememoapp.model.MemoRepository
import javax.inject.Inject

class InsertMemoUseCase @Inject constructor(private val repository : MemoRepository) {

    suspend operator fun invoke(memo : Memo){
        repository.insertMemo(memo)
    }
}