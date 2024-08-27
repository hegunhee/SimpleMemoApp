package com.hegunhee.newsimplememoapp.data.api.dto.memo.dto

import com.hegunhee.newsimplememoapp.data.api.dto.BigDecimalSerializer
import com.hegunhee.newsimplememoapp.data.api.dto.LocalDateTimeSerializer
import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

@Serializable
data class MemoRequestDto(
    @Serializable(with = LocalDateTimeSerializer::class)
    @SerialName("memoDate") val memoDate: LocalDateTime,
    @SerialName("incomeExpenseType") val incomeExpenseType: IncomeExpenseType,
    @SerialName("attribute") val attribute: String,
    @SerialName("asset") val asset: String,
    @SerialName("description") val description: String,
    @Serializable(with = BigDecimalSerializer::class)
    @SerialName("price") val price: BigDecimal
)