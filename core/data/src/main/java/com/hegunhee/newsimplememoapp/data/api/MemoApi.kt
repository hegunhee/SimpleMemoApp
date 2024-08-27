package com.hegunhee.newsimplememoapp.data.api

import com.hegunhee.newsimplememoapp.data.api.dto.memo.MemoEntity
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.AttributeMemosResponse
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.MemoIdResponse
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.MemoRequestDto
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.MemoSummaryResponses
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.StaticsMemoResponses
import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MemoApi {

    @GET("/v1/memo/{memoId}")
    suspend fun findOne(@Path("memoId") memoId : Int) : MemoEntity

    @GET("/v1/memos")
    suspend fun findMemos(
        @Query("year") year : Int,
        @Query("month") month : Int
    ) : MemoSummaryResponses

    @GET("/v1/memos/attribute/{attribute}")
    suspend fun findMemosByAttribute(
        @Path("attribute") attribute : String,
        @Query("year") year : Int,
        @Query("month") month : Int
    ) : AttributeMemosResponse

    @GET("/v1/memos/type/{type}")
    suspend fun findMemosByIncomeExpenseType(
        @Path("type") type : IncomeExpenseType,
        @Query("year") year : Int,
        @Query("month") month : Int
    ) : StaticsMemoResponses

    @POST("/v1/memo")
    suspend fun save(@Body memoRequest : MemoRequestDto) : MemoIdResponse

    @PATCH("/v1/memo/{memoId}")
    suspend fun update(@Path("memoId") memoId : Int,@Body memoRequest: MemoRequestDto) : MemoIdResponse

    @DELETE("/v1/memo/{memoId}")
    suspend fun delete(@Path("memoId") memoId : Int) : MemoIdResponse
}