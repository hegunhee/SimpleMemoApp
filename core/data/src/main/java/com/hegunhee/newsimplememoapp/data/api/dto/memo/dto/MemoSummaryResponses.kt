package com.hegunhee.newsimplememoapp.data.api.dto.memo.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemoSummaryResponses(
    @SerialName("totalSum") val totalSumResponse: TotalSumResponse,
    @SerialName("memos") val memos: MemoEntities
)