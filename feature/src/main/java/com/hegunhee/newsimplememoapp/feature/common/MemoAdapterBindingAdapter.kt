package com.hegunhee.newsimplememoapp.feature.common

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.feature.R

@BindingAdapter("setPrice")
fun TextView.setPrice(price : Int){
    this.text = "" + price.toMoneyFormat() + "원"
}

@BindingAdapter("setPriceType")
fun TextView.setPriceType(type : String){
    when (type){
        "수입" -> { this.setTextColor(ContextCompat.getColor(context, R.color.blue)) }
        "지출" -> { this.setTextColor(ContextCompat.getColor(context,R.color.red)) }
        else -> { this.setTextColor(ContextCompat.getColor(context,R.color.white)) }
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
        setBackgroundResource(R.color.blue)
    }else if(dayOfWeek == "일"){
        setBackgroundResource(R.color.red)
    }else{
        setBackgroundResource(R.color.black)
    }
    text = dayOfWeek + "요일"
}

@BindingAdapter("setDate")
fun TextView.setDate(memoDate: MemoType.MemoDate){
    text = "" + memoDate.year +"." + memoDate.month
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setDay")
fun TextView.setDay(day : Int){
    text = ""+ day
}