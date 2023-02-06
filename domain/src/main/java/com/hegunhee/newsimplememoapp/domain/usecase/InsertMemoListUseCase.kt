package com.hegunhee.newsimplememoapp.domain.usecase

import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class InsertMemoListUseCase @Inject constructor(private val repository: MemoRepository)  {
    suspend operator fun invoke(memoEntity : List<MemoType>){
        /**
         * 사용 안하는 Test Code, 추후 다른 Test Code로 변경될 수 있음
         */
//        repository.insertMemoList(memoEntity)
    }
}