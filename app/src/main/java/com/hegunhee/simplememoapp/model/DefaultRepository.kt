package com.hegunhee.simplememoapp.model

import com.hegunhee.simplememoapp.data.DB.MemoDatabase
import com.hegunhee.simplememoapp.data.Dao.DataDao
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class DefaultRepository(
    private val dao: DataDao,
    private val ioDispatcher : CoroutineDispatcher
) : Repository {
    override suspend fun getAll(): List<accountItemEntity>  = withContext(ioDispatcher){
        return@withContext dao.selectAll().sortedBy { it.price }
    }

    override suspend fun insert(entity : accountItemEntity) : Unit = withContext(ioDispatcher) {
        dao.insert(entity)
    }

//    override suspend fun get(): accountItemEntity {
//        return dao.select()
//    }
}