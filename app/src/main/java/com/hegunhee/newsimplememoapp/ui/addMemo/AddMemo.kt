package com.hegunhee.newsimplememoapp.ui.addMemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.databinding.ActivityAddMemoBinding
import org.koin.android.ext.android.inject


class AddMemo : AppCompatActivity() {

    private lateinit var binding : ActivityAddMemoBinding
    private val addMemoViewModel : AddMemoViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_memo)
        binding.viewmodel = addMemoViewModel
        binding.lifecycleOwner = this
        addMemoViewModel.initData()
        initListener()
    }

    private fun initListener(){
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.day.setOnClickListener {
            Toast.makeText(this, "day", Toast.LENGTH_SHORT).show()
        }
        binding.time.setOnClickListener {
            Toast.makeText(this, "time", Toast.LENGTH_SHORT).show()
        }
        binding.asset.setOnClickListener {
            Toast.makeText(this, "asset", Toast.LENGTH_SHORT).show()
        }
        binding.attr.setOnClickListener {
            Toast.makeText(this, "attr", Toast.LENGTH_SHORT).show()
        }

    }
}