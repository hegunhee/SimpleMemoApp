package com.hegunhee.newsimplememoapp.ui.common

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import kotlin.math.min

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