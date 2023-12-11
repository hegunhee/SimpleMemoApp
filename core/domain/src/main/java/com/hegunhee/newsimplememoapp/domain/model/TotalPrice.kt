package com.hegunhee.newsimplememoapp.domain.model

data class TotalPrice(
    val incomePrice : Int,
    val expensePrice : Int,
    val sumPrice : Int = incomePrice - expensePrice
)