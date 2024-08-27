package com.hegunhee.newsimplememoapp.data.api.dto.memo.dto

import com.hegunhee.newsimplememoapp.data.api.dto.memo.MemoEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemoEntities (
    @SerialName("memos") val memos : List<MemoEntity>
)