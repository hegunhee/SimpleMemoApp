package com.hegunhee.newsimplememoapp.domain.model.memo

import java.math.BigDecimal
import java.time.LocalDateTime

data class MemoForm (
    val memoDate : LocalDateTime,
    val incomeExpenseType: IncomeExpenseType,
    val attribute : String,
    val asset : String,
    val description : String,
    val price : BigDecimal
)