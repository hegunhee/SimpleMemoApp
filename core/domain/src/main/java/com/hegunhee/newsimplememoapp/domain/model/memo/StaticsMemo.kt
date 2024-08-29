package com.hegunhee.newsimplememoapp.domain.model.memo

import java.math.BigDecimal

data class StaticsMemo(
    val percent : Int,
    val attribute : String,
    val price : BigDecimal
)