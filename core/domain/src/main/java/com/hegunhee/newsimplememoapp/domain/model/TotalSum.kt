package com.hegunhee.newsimplememoapp.domain.model

import java.math.BigDecimal

data class TotalSum(
    val incomeSum : BigDecimal,
    val expenseSum : BigDecimal,
    val totalSum : BigDecimal
)