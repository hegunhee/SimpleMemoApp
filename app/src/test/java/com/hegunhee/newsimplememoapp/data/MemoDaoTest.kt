package com.hegunhee.newsimplememoapp.data

import androidx.room.*
import com.hegunhee.newsimplememoapp.data.Entity.Memo

@Dao
abstract class MemoTestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend abstract fun addMemo(memo : Memo)

    @Delete
    suspend abstract fun deleteMemo(memo : Memo)

    @Query("SELECT * FROM Memo")
    suspend abstract fun getAllMemo() : List<Memo>

    @Query("SELECT * FROM Memo where id = :id")
    suspend abstract fun getMemo(id : Int) : Memo

}