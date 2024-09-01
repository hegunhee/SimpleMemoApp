package com.hegunhee.newsimplememoapp.domain.model.memo

import com.hegunhee.newsimplememoapp.domain.model.getDateStamp
import com.hegunhee.newsimplememoapp.domain.model.getTimeStamp
import com.hegunhee.newsimplememoapp.domain.model.patternNow
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class MemoForm (
    val memoDate : LocalDateTime,
    val incomeExpenseType: IncomeExpenseType,
    val attribute : String,
    val asset : String,
    val description : String,
    val price : BigDecimal
) {

    fun isFullForm() : Pair<Boolean,String> {
        if (attribute == "") return Pair(false,"attribute")
        if (asset == "") return Pair(false,"asset")
        if (price == BigDecimal.ZERO) return Pair(false,"price")
        return Pair(true,"ok")
    }

    fun updateDate(updatedDate : LocalDate) : MemoForm {
        val time = memoDate.toLocalTime()
        return copy(memoDate = LocalDateTime.of(updatedDate,time))
    }

    fun updateTime(updatedTime : LocalTime) : MemoForm {
        val date = memoDate.toLocalDate()
        return copy(memoDate = LocalDateTime.of(date,updatedTime))
    }

    fun updatePrice(updatedPrice : Int) : MemoForm {
        return copy(price = BigDecimal.valueOf(updatedPrice.toLong()))
    }

    fun updateDesc(updatedDescription : String) : MemoForm {
        return copy(description = updatedDescription)
    }
    fun getDateStamp() : String {
        return memoDate.getDateStamp()
    }

    fun getTimeStamp() : String {
        return memoDate.getTimeStamp()
    }

    companion object {
        fun init() : MemoForm = MemoForm(memoDate = patternNow(),IncomeExpenseType.EXPENSE,"","","",BigDecimal.ZERO)
    }
}