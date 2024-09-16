package com.hegunhee.newsimplememoapp.domain.model.memo

import com.hegunhee.newsimplememoapp.domain.model.TotalSum

data class MemosSummary(
    val totalSum : TotalSum,
    val memos : List<Memo>
)
