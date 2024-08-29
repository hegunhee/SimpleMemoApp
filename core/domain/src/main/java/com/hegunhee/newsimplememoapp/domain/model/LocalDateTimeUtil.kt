package com.hegunhee.newsimplememoapp.domain.model

import java.time.LocalDateTime

public fun LocalDateTime.isMorning() : Boolean {
    return hour <= 12
}

public fun LocalDateTime.getDateStamp() : String {
    return "${year}/${month}/${dayOfMonth} (${dayOfWeek.name})"
}

public fun LocalDateTime.amPm() : String{
    return if(isMorning()) {
        "오전"
    } else {
        "오후"
    }
}
public fun LocalDateTime.getTimeStamp() : String {
    return "${amPm()} ${hour}:${minute}"
}