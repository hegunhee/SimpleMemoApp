package com.hegunhee.newsimplememoapp.data.api.dto.memo.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemoIdResponse(
    @SerialName("memoId") val memoId: Int
)