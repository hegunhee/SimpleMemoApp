package com.hegunhee.newsimplememoapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarMenu
import com.google.android.material.navigation.NavigationBarMenuView
import com.google.android.material.navigation.NavigationBarView
import com.hegunhee.newsimplememoapp.data.DB.provideDB
import com.hegunhee.newsimplememoapp.data.Entity.Memo
import com.hegunhee.newsimplememoapp.data.Entity.getTwentyMockingMemo
import com.hegunhee.newsimplememoapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()

    }

    private fun initNavigation(){
        binding.bottomNavigation.run {
            setOnItemSelectedListener { item ->
                when(item.itemId){
                    R.id.memo_menu -> {
                        Toast.makeText(this@MainActivity, "Click memo_menu", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }
                    R.id.statistics_menu ->{
                        Toast.makeText(this@MainActivity, "Click statics_menu", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }
                    else -> false
                }
            }
        }
    }

}