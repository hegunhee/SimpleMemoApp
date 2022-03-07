package com.hegunhee.newsimplememoapp.domain.memoUsecase

import com.hegunhee.newsimplememoapp.data.Entity.Memo
import com.hegunhee.newsimplememoapp.domain.UseCase
import com.hegunhee.newsimplememoapp.model.MemoRepository

class GetAllMemoUseCase(val repository: MemoRepository) :UseCase{

    suspend operator fun invoke(): List<Memo> {
        return repository.getAllMemo()
    }
}