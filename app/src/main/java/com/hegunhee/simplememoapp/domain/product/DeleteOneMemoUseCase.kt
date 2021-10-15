package com.hegunhee.simplememoapp.domain.product

import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.domain.UseCase
import com.hegunhee.simplememoapp.model.Repository

class DeleteOneMemoUseCase(private val repository: Repository) : UseCase {
    suspend operator fun invoke(accountItemEntity: accountItemEntity){
        repository.delete(accountItemEntity)
    }
}