package com.example.albumlistproject.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.albumlistproject.R
import kotlinx.android.synthetic.main.activity_album_list.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AlbumListActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(AlbumListActivity::class.java)

    @Test
    fun isRecyclerViewDisplayed() {
        Thread.sleep(2000)
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }
}