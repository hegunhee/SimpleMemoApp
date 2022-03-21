package com.hegunhee.newsimplememoapp.ui.addMemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        initListener()
    }

    private fun initListener(){
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }
}