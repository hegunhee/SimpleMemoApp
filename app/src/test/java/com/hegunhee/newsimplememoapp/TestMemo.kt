package com.hegunhee.newsimplememoapp

data class TestMemo(
    val category : String,
    val price : Int
){
    companion object{
        val test_memo = TestMemo("지출",10000)
    }
}
