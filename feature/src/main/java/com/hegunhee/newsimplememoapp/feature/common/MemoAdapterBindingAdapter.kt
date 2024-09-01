package com.hegunhee.newsimplememoapp.feature.common

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.hegunhee.newsimplememoapp.feature.R

@BindingAdapter("setPrice")
fun TextView.setPrice(price : Int){
    text = "${price.toMoneyFormat()}원"
}

@BindingAdapter("setDayOfWeek")
fun TextView.setDayOfWeek(dayOfWeek: String) {
    when (dayOfWeek) {
        "토" -> { setBackgroundResource(R.color.blue) }
        "일" -> { setBackgroundResource(R.color.red) }
        else -> { setBackgroundResource(R.color.black) }
    }
    text = "${dayOfWeek}요일"
}