package com.hegunhee.newsimplememoapp.data.dao

import androidx.room.*
import com.hegunhee.newsimplememoapp.data.entity.MemoEntity

@Dao
interface MemoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMemo(memoEntity : MemoEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllMemo(vararg memoEntities : MemoEntity)


    @Query("SELECT * FROM Memo where id = :id")
    suspend fun getMemo(id : Int) : MemoEntity

    @Query("SELECT * FROM Memo where year = :year AND month = :month ORDER BY day DESC")
    suspend fun getMemoListSortedByYearAndMonth(year : Int, month : Int) : List<MemoEntity>

    @Query("SELECT * FROM Memo where year = :year AND month = :month AND attr = :attr ORDER BY day DESC")
    suspend fun getMemoListSortedByAttrYearMonth(attr : String,year : Int,month : Int) : List<MemoEntity>

    @Update
    suspend fun updateMemo(memoEntity : MemoEntity)

    @Query("DELETE FROM Memo where id = :id")
    suspend fun deleteMemo(id : Int)

    @Query("DELETE FROM Memo")
    suspend fun deleteAllMemo()

}