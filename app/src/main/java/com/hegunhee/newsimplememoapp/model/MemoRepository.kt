package com.hegunhee.newsimplememoapp.model

import com.hegunhee.newsimplememoapp.data.Entity.Memo

interface MemoRepository {

    suspend fun addMemo(memo : Memo)

    suspend fun getAllMemo() : List<Memo>

    suspend fun deleteAllMemo()

    suspend fun deleteMemo(memo : Memo)
}