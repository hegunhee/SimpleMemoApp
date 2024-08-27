package com.hegunhee.newsimplememoapp.data.api.dto.memo.dto

import com.hegunhee.newsimplememoapp.data.api.dto.BigDecimalSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class StaticsMemoResponse(
    @SerialName("percent") val percent : Int,
    @SerialName("attribute") val attribute : String,
    @Serializable(with = BigDecimalSerializer::class)
    @SerialName("price") val price : BigDecimal
)