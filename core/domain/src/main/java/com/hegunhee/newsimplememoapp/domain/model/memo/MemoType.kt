package com.hegunhee.newsimplememoapp.domain.model.memo

import com.hegunhee.newsimplememoapp.domain.model.getTimeStamp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime

sealed class MemoType() {

    data class Memo(
        val id : Int,
        val incomeExpenseType: IncomeExpenseType,
        val date : LocalDateTime,
        val asset : String,
        val attr : String,
        val price : Int,
        val desc : String
    ) : MemoType() {

        fun timeStamp() : String{
            return date.getTimeStamp()
        }

    }

    data class MemoDate(
        val date : LocalDate,
        val incomeSum : Int,
        val expenseSum : Int
    ) : MemoType() {

        fun dateStamp() : String {
            return "${date.year}.${date.monthValue}"
        }

        fun dayOfWeek() : String {
            return englishToKoreanDayOfWeek()
        }

        private fun englishToKoreanDayOfWeek() : String {
            return when(date.dayOfWeek) {
                DayOfWeek.MONDAY -> "월"
                DayOfWeek.TUESDAY -> "화"
                DayOfWeek.WEDNESDAY -> "수"
                DayOfWeek.THURSDAY -> "목"
                DayOfWeek.FRIDAY -> "금"
                DayOfWeek.SATURDAY -> "토"
                DayOfWeek.SUNDAY -> "일"
            }
        }
    }
}

fun Memo.toMemo() : MemoType.Memo {
    return MemoType.Memo(id,incomeExpenseType,memoDate,asset,attribute,price.intValueExact(),description)
}

fun List<Memo>.toMemoTypes() : List<MemoType>{
    return this.groupBy { it.memoDate.dayOfMonth }
        .flatMap { (day, list) ->
            val date = list[0].memoDate.toLocalDate()
            val incomeSum = list.filter { it.incomeExpenseType == IncomeExpenseType.INCOME }.sumOf { it.price }
            val expenseSum = list.filter { it.incomeExpenseType == IncomeExpenseType.EXPENSE }.sumOf { it.price }
            val memoDate = MemoType.MemoDate(date,incomeSum.intValueExact(),expenseSum.intValueExact())
            listOf(memoDate) + list.map { it.toMemo() }
        }.toList()
}