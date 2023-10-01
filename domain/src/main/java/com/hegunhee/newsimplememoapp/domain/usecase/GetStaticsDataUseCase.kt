package com.hegunhee.newsimplememoapp.domain.usecase

import com.hegunhee.newsimplememoapp.domain.model.StaticsData
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class GetStaticsDataUseCase @Inject constructor(private val repository : MemoRepository) {
    suspend operator fun invoke(year : Int, month: Int) : List<StaticsData>{
        return repository.getStaticsData(year,month)
    }
}