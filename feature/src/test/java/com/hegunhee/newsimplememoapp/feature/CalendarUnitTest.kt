package com.hegunhee.newsimplememoapp.feature

import com.hegunhee.newsimplememoapp.feature.common.diffUtil
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.Calendar
import java.util.Date

/**
 * 오늘 날짜정보를 적을때 직접 프로퍼티 값을 조절해야함
 * 보다 나은 방법이 있으면 좋을듯하다
 */
class CalendarUnitTest {

    private lateinit var calendar : Calendar

    private val todayYear = 2023
    private val todayMonth = 9
    private val todayDay = 5
    private val todayDayOfWeek = "화"

    private val todayDate = arrayOf(todayYear,todayMonth,todayDay)

    private val koreaDayOfWeek = arrayOf("일","월","화","수","목","금","토")

    @Before
    fun `init calendar`() {
        calendar = Calendar.getInstance().apply {
            time = Date()
        }
    }

    @Test
    fun `year month day test`() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val date = arrayOf(year,month,day)
        assertArrayEquals(todayDate,date)
    }

    // 오후 11시의 경우
    //HOUR_OF_DAY -> 23시
    //HOUR -> 11시
    @Test
    fun `hour minute second test`() {
        println(calendar.get(Calendar.HOUR))
        println(calendar.get(Calendar.MINUTE))
        println(calendar.get(Calendar.SECOND))
        assert(true)
    }

    @Test
    fun `day of week test`() {
        val dayOfWeek = koreaDayOfWeek[calendar.get(Calendar.DAY_OF_WEEK)-1]
        assertEquals(todayDayOfWeek,dayOfWeek)
    }

    /**
     * Calender 객체는 각자 존재함
     */
    @Test
    fun `is calendar instance singleton`() {
        val beforeMonth = calendar.get(Calendar.MONTH)
        Calendar.getInstance().apply {
            add(Calendar.MONTH,1)
        }
        val afterMonth = calendar.get(Calendar.MONTH)
        assertEquals(beforeMonth,afterMonth)
    }

    @Test
    fun `calc diff dayOfWeek`() {

        val diffCalendar = Calendar.getInstance().apply {
             set(2023,8-1,5)
        }
        val expectDayOfWeek = koreaDayOfWeek[diffCalendar.get(Calendar.DAY_OF_WEEK) -1]

        assertEquals("토",expectDayOfWeek)

    }
}