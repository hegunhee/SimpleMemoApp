package com.hegunhee.newsimplememoapp.domain.model.memo

import java.math.BigDecimal

data class AttributeMemos(
    val price : BigDecimal,
    val attribute : String,
    val memos : List<MemoServer>
)