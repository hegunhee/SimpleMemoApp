package com.hegunhee.newsimplememoapp.data.Dao

import androidx.room.*
import com.hegunhee.newsimplememoapp.data.Entity.Memo

@Dao
interface MemoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMemo(memo : Memo)

    @Delete
    suspend fun deleteMemo(memo : Memo)

    @Query("SELECT * FROM Memo")
    suspend fun getAllMemo() : List<Memo>

    @Query("SELECT * FROM Memo where id = :id")
    suspend fun getMemo(id : Int) : Memo

}