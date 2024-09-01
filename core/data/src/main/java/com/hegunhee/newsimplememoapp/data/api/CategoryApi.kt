package com.hegunhee.newsimplememoapp.data.api

import com.hegunhee.newsimplememoapp.data.api.dto.category.CategoryEntity
import com.hegunhee.newsimplememoapp.data.api.dto.category.dto.CategoryNameResponse
import com.hegunhee.newsimplememoapp.data.api.dto.category.dto.CategoryNamesByTypeResponse
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoryApi {

    @GET("/v1/category/{categoryName}")
    suspend fun findOne(@Path("categoryName") categoryName : String) : CategoryEntity

    @GET("/v1/categories/categoryType/{categoryType}")
    suspend fun findAllBy(@Path("categoryType") type : CategoryType) : CategoryNamesByTypeResponse

    @GET("/v1/category/existence/{categoryName}")
    suspend fun existsBy(@Path("categoryName") name : String) : Boolean

    @POST("/v1/category")
    suspend fun save(@Body category : CategoryEntity) : CategoryNameResponse

    @DELETE("/v1/category/{categoryName}")
    suspend fun delete(@Path("categoryName") name : String) : CategoryNameResponse
}