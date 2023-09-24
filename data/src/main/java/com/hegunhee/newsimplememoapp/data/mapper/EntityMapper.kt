package com.hegunhee.newsimplememoapp.data.mapper

import com.hegunhee.newsimplememoapp.data.entity.MemoEntity
import com.hegunhee.newsimplememoapp.domain.model.MemoType

fun MemoEntity.toMemo() : MemoType.Memo {
    return MemoType.Memo(category, year, month, day, dayOfWeek, amPm, hour, minute, attr, price, asset, description, id)
}

fun MemoType.Memo.toMemoEntity() : MemoEntity {
    return MemoEntity(category, year, month, day, dayOfWeek, amPm, hour, minute, attr, price, asset, description, id)
}