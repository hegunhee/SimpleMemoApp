package com.hegunhee.newsimplememoapp.domain.memoUsecase

import com.hegunhee.newsimplememoapp.data.entity.Memo
import com.hegunhee.newsimplememoapp.model.MemoRepository
import javax.inject.Inject

class InsertMemoListUseCase @Inject constructor(private val repository: MemoRepository)  {
    suspend operator fun invoke(memo : List<Memo>){
        repository.insertMemoList(memo)
    }
}