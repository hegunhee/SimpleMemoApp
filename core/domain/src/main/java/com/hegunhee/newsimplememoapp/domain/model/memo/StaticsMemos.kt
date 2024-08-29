package com.hegunhee.newsimplememoapp.domain.model.memo

import java.math.BigDecimal

data class StaticsMemos(
    val type : IncomeExpenseType,
    val year : Int,
    val month : Int,
    val totalPrice : BigDecimal,
    val staticsMemos : List<StaticsMemo>
)