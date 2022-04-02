package com.hegunhee.newsimplememoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.data.DB.provideDB
import com.hegunhee.newsimplememoapp.data.DB.provideToDoDao
import com.hegunhee.newsimplememoapp.data.entity.getTwentyMockingMemo
import com.hegunhee.newsimplememoapp.databinding.ActivityMainBinding
import com.hegunhee.newsimplememoapp.ui.memo.MemoFragment
import com.hegunhee.newsimplememoapp.ui.statics.StaticsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("lifeCycle","MainActivityOnCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initNavigation()
        initData()
    }

    private fun initData(){
    }
    private fun initViews()  {
        showFragment(MemoFragment.newInstance(),MemoFragment.TAG)
    }
    private fun initNavigation(){
        binding.bottomNavigation.run {
            setOnItemSelectedListener { item ->
                when(item.itemId){
                    R.id.memo_menu -> {
                        showFragment(MemoFragment.newInstance(),MemoFragment.TAG)
                        true
                    }
                    R.id.statistics_menu ->{
                        showFragment(StaticsFragment.newInstance(),StaticsFragment.TAG)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun showFragment(fragment: Fragment, tag: String){
        val findFragment = supportFragmentManager.findFragmentByTag(tag)
        supportFragmentManager.fragments.forEach{fm ->
            supportFragmentManager.beginTransaction().hide(fm).commitAllowingStateLoss()
        }
        findFragment?.let {
            supportFragmentManager.beginTransaction().show(it).commitAllowingStateLoss()
        } ?: run{
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container,fragment,tag)
                .commitAllowingStateLoss()
        }
    }

}