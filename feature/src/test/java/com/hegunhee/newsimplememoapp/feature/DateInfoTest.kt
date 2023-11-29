package com.hegunhee.newsimplememoapp.feature

import com.hegunhee.newsimplememoapp.domain.model.DateInfo
import org.junit.Assert.assertEquals
import org.junit.Test

class DateInfoTest {

    @Test
    fun `date info dateStamp Test`() {
        val year = 2023
        val month = 9
        val day = 18
        val dayOfWeek = "월"
        val dateStamp = "${year}/${month}/${day} (${dayOfWeek})"
        val dateInfo = DateInfo(year,month,day,dayOfWeek)
        println(dateInfo.dateStamp)
        assertEquals(dateInfo.dateStamp,dateStamp)
    }
}