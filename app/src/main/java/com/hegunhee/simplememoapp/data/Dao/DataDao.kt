package com.hegunhee.simplememoapp.data.Dao

import androidx.room.*
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(accountEntity: accountItemEntity): Long

    @Query("SELECT * FROM accountItemEntity")
    suspend fun selectAll() : List<accountItemEntity>

//    @Query("SELECT * FROM accountItemEntity where id =:id")
//    suspend fun select(id : Int) : accountItemEntity

    @Delete
    suspend fun delete(entity : accountItemEntity)

}