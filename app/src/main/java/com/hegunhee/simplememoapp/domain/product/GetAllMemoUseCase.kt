package com.hegunhee.simplememoapp.domain.product

import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.domain.UseCase
import com.hegunhee.simplememoapp.model.DefaultRepository
import com.hegunhee.simplememoapp.model.Repository

class GetAllMemoUseCase(private val defaultRepository : Repository) : UseCase {

    suspend operator fun invoke(): List<accountItemEntity> {
        return defaultRepository.getAll()
    }
}