package com.hegunhee.newsimplememoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.databinding.ActivityMainBinding
import com.hegunhee.newsimplememoapp.ui.memo.MemoFragment
import com.hegunhee.newsimplememoapp.ui.statics.StaticsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("lifeCycle","MainActivityOnCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_main_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        with(binding){
            bottomNavigation.setupWithNavController(navController)
        }
    }


}