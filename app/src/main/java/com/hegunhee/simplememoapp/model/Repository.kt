package com.hegunhee.simplememoapp.model

import com.hegunhee.simplememoapp.data.Entity.accountItemEntity

interface Repository {
    suspend fun getAll() : List<accountItemEntity>

//    suspend fun get() : accountItemEntity

    suspend fun delete(entity: accountItemEntity)

    suspend fun insert(entity : accountItemEntity)
}