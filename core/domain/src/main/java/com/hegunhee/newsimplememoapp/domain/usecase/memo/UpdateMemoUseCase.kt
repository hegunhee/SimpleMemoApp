package com.hegunhee.newsimplememoapp.domain.usecase.memo

import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class UpdateMemoUseCase @Inject constructor(private val repository: MemoRepository){

    suspend operator fun invoke(memo : MemoType.Memo){
        repository.updateMemo(memo)
    }
}