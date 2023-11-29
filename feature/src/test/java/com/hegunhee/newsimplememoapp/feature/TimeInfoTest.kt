package com.hegunhee.newsimplememoapp.feature

import com.hegunhee.newsimplememoapp.domain.model.TimeInfo
import org.junit.Assert.assertEquals
import org.junit.Test

class TimeInfoTest {

    @Test
    fun `test time info timeStamp`() {
        val hour = 1
        val minute = 20
        val ampm = "오후"
        val timeStamp = "$ampm ${hour}:${minute}"
        val timeInfo = TimeInfo(hour = hour, minute = minute,ampm = ampm)
        println(timeInfo.timeStamp)
        assertEquals(timeInfo.timeStamp,timeStamp)
    }
}