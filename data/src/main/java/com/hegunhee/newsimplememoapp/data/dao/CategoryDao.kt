package com.hegunhee.newsimplememoapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hegunhee.newsimplememoapp.data.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category where categoryCode = :categoryCode")
    suspend fun getAllCategoryByType(categoryCode : Int) : List<CategoryEntity>

    @Query("DELETE FROM category where text = :text")
    suspend fun deleteCategory(text : String)

    @Query("SELECT * FROM category where categoryCode = :categoryCode AND text = :text")
    suspend fun getCategoryOrNull(categoryCode : Int, text : String) : CategoryEntity?

    @Insert
    suspend fun insertCategory(categoryEntity: CategoryEntity)
}