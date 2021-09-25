package com.hegunhee.simplememoapp.presentation.Main

import android.os.Bundle
import android.widget.Toast
import com.hegunhee.simplememoapp.databinding.ActivityMainBinding
import com.hegunhee.simplememoapp.presentation.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MainActivity:  BaseActivity<MainViewModel,ActivityMainBinding>(){
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() = with(binding){
        addMemo.setOnClickListener {

        }
    }

    override fun observeData() = viewModel.liveData.observe(this){
        when(it){
            is MainState.Uninitalized ->{
                Toast.makeText(this, "Uninitalized", Toast.LENGTH_SHORT).show()
            }
            is MainState.Loading -> {
                Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

}