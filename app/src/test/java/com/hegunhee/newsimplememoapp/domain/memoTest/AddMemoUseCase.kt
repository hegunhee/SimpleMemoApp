package com.hegunhee.newsimplememoapp.domain.memoTest

import com.hegunhee.newsimplememoapp.data.Memo
import com.hegunhee.newsimplememoapp.domain.UseCase
import com.hegunhee.newsimplememoapp.model.MemoRepository

class AddMemoUseCase(
    val repository : MemoRepository
) : UseCase {

    suspend operator fun invoke(memo : Memo){
        repository.addMemo(memo)
    }
}