package com.hegunhee.newsimplememoapp.daoTest

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(person : Person)

    @Query("SELECT * FROM Person")
    suspend fun selectAll() : List<Person>
}