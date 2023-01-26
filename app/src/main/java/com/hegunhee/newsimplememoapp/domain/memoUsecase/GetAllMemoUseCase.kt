package com.hegunhee.newsimplememoapp.domain.memoUsecase

import com.hegunhee.newsimplememoapp.data.entity.MemoEntity
import com.hegunhee.newsimplememoapp.model.MemoRepository
import javax.inject.Inject

class GetAllMemoUseCase @Inject constructor(private val repository: MemoRepository) {

    suspend operator fun invoke(): List<MemoEntity> {
        return repository.getAllMemo()
    }
}