package com.hegunhee.newsimplememoapp.data.mapper

import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.MemoRequestDto
import com.hegunhee.newsimplememoapp.data.entity.MemoEntity
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.model.memo.MemoForm

fun MemoEntity.toMemo() : MemoType.Memo {
//    return MemoType.Memo(id,category, year, month, day, dayOfWeek, amPm, hour, minute, attr, price, asset, description)
    return MemoType.Memo.empty
}

fun MemoType.Memo.toMemoEntity() : MemoEntity {
    return MemoEntity(category, year, month, day, dayOfWeek, amPm, hour, minute, attr, price, asset, description, id)
}