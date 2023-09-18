package com.hegunhee.newsimplememoapp.domain.model


sealed class MemoType() {

    data class Memo(
        val category: String,
        val year: Int,
        val month: Int,
        val day: Int,
        val dayOfWeek: String,
        val amPm: String,
        val hour: Int,
        val minute: Int,
        val attr: String,
        val price: Int,
        val asset : String,
        val description: String,
        var id: Int = 0
    ) : MemoType() {

        companion object {
            val empty = Memo("",0,0,0,"","",0,0,"",0,"","",0)
        }
    }

    data class MemoDate(
        val year : Int,
        val month : Int,
        val day : Int,
        val dayOfWeek : String,
        val incomeSum : Int,
        val expenseSum : Int
    ): MemoType()
}