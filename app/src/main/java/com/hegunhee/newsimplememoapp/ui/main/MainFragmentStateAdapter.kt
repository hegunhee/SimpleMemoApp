package com.hegunhee.newsimplememoapp.ui.main

import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.ui.memo.MemoFragment
import com.hegunhee.newsimplememoapp.ui.statics.StaticsFragment


enum class MainTab(
    val fragment :() -> Fragment,
    val menuRes : Int
){
    MEMO({MemoFragment()}, R.id.memo),
    STATICS({StaticsFragment()},R.id.statics);

    companion object {
        fun getFragment(position: Int) = values()[position].fragment.invoke()
        fun getPosition(menuItem: MenuItem) = values().first { it.menuRes == menuItem.itemId }.ordinal
    }
}
class MainFragmentStateAdapter(
    fragment : MainFragment,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragment.childFragmentManager,lifecycle){
    override fun createFragment(position: Int): Fragment = MainTab.getFragment(position)
    override fun getItemCount(): Int = MainTab.values().size
}