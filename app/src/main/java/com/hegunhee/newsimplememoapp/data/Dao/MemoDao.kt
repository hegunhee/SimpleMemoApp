package com.hegunhee.newsimplememoapp.data.Dao

import androidx.room.*
import com.hegunhee.newsimplememoapp.data.entity.Memo

@Dao
interface MemoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memo : Memo)

    @Delete
    suspend fun deleteMemo(memo : Memo)

    @Query("DELETE FROM Memo")
    suspend fun deleteAllMemo()

    @Query("SELECT * FROM Memo")
    suspend fun getAllMemo() : List<Memo>

    @Query("SELECT * FROM Memo where id = :id")
    suspend fun getMemo(id : Int) : Memo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMemo(vararg memos : Memo)

    @Query("SELECT * FROM Memo where year = :year AND month = :month ORDER BY day DESC")
    suspend fun getMemoListSortedByYearAndMonth(year : Int, month : Int) : List<Memo>

    @Query("SELECT * FROM Memo where year = :year AND month = :month AND category = :category ORDER BY day DESC")
    suspend fun getMemoListSortedByCategoryAndYearAndMonth(category : String,year : Int,month : Int) : List<Memo>

    @Query("SELECT * FROM Memo where year = :year AND month = :month AND attr = :attr ORDER BY day DESC")
    suspend fun getMemoListSortedByAttrYearMonth(attr : String,year : Int,month : Int) : List<Memo>

}