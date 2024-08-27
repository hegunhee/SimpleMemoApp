package com.hegunhee.newsimplememoapp.data.api.dto.memo.dto

import com.hegunhee.newsimplememoapp.data.api.dto.BigDecimalSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class TotalSum(
    @Serializable(with = BigDecimalSerializer::class)
    @SerialName("incomeSum") val incomeSum: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    @SerialName("expenseSum") val expenseSum: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    @SerialName("totalSum") val totalSum: BigDecimal,
)
