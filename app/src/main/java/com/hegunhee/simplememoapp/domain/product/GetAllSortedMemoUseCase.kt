package com.hegunhee.simplememoapp.domain.product

import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.domain.UseCase
import com.hegunhee.simplememoapp.model.DefaultRepository
import com.hegunhee.simplememoapp.model.Repository

class GetAllSortedMemoUseCase(private val repository : Repository) : UseCase {

    suspend operator fun invoke(): List<accountItemEntity> {
        return repository.getAll().sortedBy { it.price }
    }
}