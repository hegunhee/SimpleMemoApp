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

fun isStandardMemo(
    category: String,
    asset : String,
    attr: String,
    price : String,
) : Boolean {
    if(category.isBlank()) return false
    if(asset.isBlank()) return false
    if(attr.isBlank()) return false
    if(price.toIntOrNull() == null || price.isBlank()) return false
    return true
}