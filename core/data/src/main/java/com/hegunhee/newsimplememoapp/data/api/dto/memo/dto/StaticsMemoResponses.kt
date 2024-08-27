package com.hegunhee.newsimplememoapp.data.api.dto.memo.dto

import com.hegunhee.newsimplememoapp.data.api.dto.BigDecimalSerializer
import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class StaticsMemoResponses (
    @SerialName("type")val type : IncomeExpenseType,
    @SerialName("year")val year : Int,
    @SerialName("month")val month : Int,
    @Serializable(with = BigDecimalSerializer::class)
    @SerialName("totalPrice")val totalPrice : BigDecimal,
    @SerialName("staticsMemos")val staticsMemos : List<StaticsMemoResponse>
)