package com.hegunhee.newsimplememoapp.feature

import androidx.fragment.app.testing.launchFragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hegunhee.newsimplememoapp.feature.memo.MemoFragment

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MemoFragmentTest {

    @Test
    fun testMemoFragment() {
        val scenario = launchFragment<MemoFragment>()

        onView(withId(R.id.date_text))
            .perform(click())
    }

}