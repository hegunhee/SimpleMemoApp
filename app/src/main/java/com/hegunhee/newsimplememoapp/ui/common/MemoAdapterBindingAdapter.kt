package com.hegunhee.newsimplememoapp.ui.common

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.domain.model.MemoType

@BindingAdapter("setPrice")
fun TextView.setPrice(price : Int){
    this.text = "" + price.toMoneyFormat() + "원"
}

@BindingAdapter("setPriceType")
fun TextView.setPriceType(type : String){
    when (type){
        "수입" -> { this.setTextColor(R.color.blue) }
        "지출" -> { this.setTextColor(R.color.red) }
        else -> { this.setTextColor(R.color.white) }
    }
}

@BindingAdapter("setTime")
fun TextView.setTime(memo : MemoType.Memo){
    val minute = if(memo.minute < 10) {
        "0${memo.minute}"
    }else {
        memo.minute.toString()
    }
    text = memo.amPm +" "+ memo.hour +":" + minute
}

@BindingAdapter("setDayOfWeek")
fun TextView.setDayOfWeek(dayOfWeek : String){
    if(dayOfWeek == "토"){
        setBackgroundColor(R.color.blue)
    }else if(dayOfWeek == "일"){
        setBackgroundColor(R.color.red)
    }else{
        setBackgroundColor(R.color.gray)
    }
    text = dayOfWeek + "요일"
}

@BindingAdapter("setDate")
fun TextView.setDate(memoDate: MemoType.MemoDate){
    text = "" + memoDate.year +"." + memoDate.month
}

@BindingAdapter("setDay")
fun TextView.setDay(day : Int){
    text = ""+ day
}