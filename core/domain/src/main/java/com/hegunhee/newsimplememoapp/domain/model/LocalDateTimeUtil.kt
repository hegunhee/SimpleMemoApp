package com.hegunhee.newsimplememoapp.domain.model

import java.time.LocalDateTime

public object LocalDateTimeUtil {

}

public fun LocalDateTime.isMorning() : Boolean {
    return hour <= 12
}

public fun LocalDateTime.getDateStamp() : String {
    return "${year}/${monthValue}/${dayOfMonth} (${dayOfWeek.name})"
}

public fun LocalDateTime.getTimeStamp() : String {
    return "${hour}:${minute}"
}

fun patternNow() : LocalDateTime{
    val now = LocalDateTime.now()
    return LocalDateTime.of(now.year,now.monthValue,now.dayOfMonth,now.hour,now.minute,now.second)
}