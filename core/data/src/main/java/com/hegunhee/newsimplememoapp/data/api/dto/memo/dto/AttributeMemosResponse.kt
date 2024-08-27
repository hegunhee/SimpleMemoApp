package com.hegunhee.newsimplememoapp.data.api.dto.memo.dto

import com.hegunhee.newsimplememoapp.data.api.dto.BigDecimalSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class AttributeMemosResponse(
    @Serializable(with = BigDecimalSerializer::class)
    @SerialName("totalPrice") val price: BigDecimal,
    @SerialName("attribute") val attribute : String,
    @SerialName("memos") val memos : MemoEntities
)