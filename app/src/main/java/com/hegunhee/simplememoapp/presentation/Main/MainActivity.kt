package com.hegunhee.simplememoapp.presentation.Main

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hegunhee.simplememoapp.R
import com.hegunhee.simplememoapp.databinding.ActivityMainBinding
import com.hegunhee.simplememoapp.presentation.Memo.MemoFragment
import com.hegunhee.simplememoapp.presentation.Statis.StatisFragment

internal class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()

    }
    private fun initViews() = with(binding){
        mainBottomNavigation.setOnNavigationItemSelectedListener(this@MainActivity)
        showFragment(MemoFragment.newInstance(),MemoFragment.TAG)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_memo ->{
                showFragment(MemoFragment.newInstance(),MemoFragment.TAG)
                true
            }
            R.id.menu_statis ->{
                showFragment(StatisFragment.newInstance(),StatisFragment.TAG)
                true
            }
            R.id.menu_asset ->{
                Toast.makeText(this@MainActivity, "asset 미구현 기능", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_settings ->{
                Toast.makeText(this@MainActivity, "menu 미구현 기능", Toast.LENGTH_SHORT).show()
                true
            }
            else -> false
        }

    }
    fun showFragment(fragment: Fragment, tag: String){
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