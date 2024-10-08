package com.hegunhee.newsimplememoapp.data.mapper

import com.hegunhee.newsimplememoapp.data.api.dto.category.CategoryEntity
import com.hegunhee.newsimplememoapp.data.api.dto.category.dto.CategoryNamesByTypeResponse
import com.hegunhee.newsimplememoapp.data.api.dto.memo.MemoEntity
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.AttributeMemosResponse
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.MemoEntities
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.MemoRequestDto
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.MemoSummaryResponses
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.StaticsMemoResponse
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.StaticsMemoResponses
import com.hegunhee.newsimplememoapp.data.api.dto.memo.dto.TotalSumResponse
import com.hegunhee.newsimplememoapp.domain.model.TotalSum
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryNamesByType
import com.hegunhee.newsimplememoapp.domain.model.category.Category
import com.hegunhee.newsimplememoapp.domain.model.memo.AttributeMemos
import com.hegunhee.newsimplememoapp.domain.model.memo.MemoForm
import com.hegunhee.newsimplememoapp.domain.model.memo.Memo
import com.hegunhee.newsimplememoapp.domain.model.memo.MemosSummary
import com.hegunhee.newsimplememoapp.domain.model.memo.StaticsMemo
import com.hegunhee.newsimplememoapp.domain.model.memo.StaticsMemos

fun MemoForm.toMemoRequest() : MemoRequestDto {
    return MemoRequestDto(memoDate,incomeExpenseType, attribute, asset, description, price)
}

fun MemoEntity.toMemo() : Memo {
    return Memo(id,memoDate,incomeExpenseType, attribute, asset, description, price)
}

fun MemoEntities.toMemos() : List<Memo> {
    return this.memos.map { it.toMemo() }
}

fun StaticsMemoResponse.toStaticsMemo() : StaticsMemo {
    return StaticsMemo(percent, attribute, price)
}

fun List<StaticsMemoResponse>.toStaticsMemos() : List<StaticsMemo> {
    return this.map { it.toStaticsMemo() }
}

fun StaticsMemoResponses.toStaticsMemos() : StaticsMemos {
    return StaticsMemos(type,year,month,totalPrice,staticsMemos.toStaticsMemos())
}

fun AttributeMemosResponse.toAttributeMemos() : AttributeMemos {
    return AttributeMemos(price,attribute,memos.toMemos())
}

fun TotalSumResponse.toTotalSum() : TotalSum {
    return TotalSum(incomeSum, expenseSum, totalSum)
}
fun MemoSummaryResponses.toMemosSummary() : MemosSummary {
    return MemosSummary(totalSumResponse.toTotalSum(),memos.toMemos())
}

fun CategoryEntity.toCategory() : Category {
    return Category(type,name)
}

fun CategoryNamesByTypeResponse.toCategoriesByType() : CategoryNamesByType {
    return CategoryNamesByType(type,names)
}