package com.hegunhee.newsimplememoapp.ui.detailMemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.data.entity.Memo
import com.hegunhee.newsimplememoapp.databinding.ActivityDetailMemoBinding
import org.koin.android.ext.android.inject

class DetailMemo : AppCompatActivity() {

    private val viewModel : DetailMemoViewModel by inject()
    private lateinit var binding : ActivityDetailMemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_memo)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        val memo = intent.getParcelableExtra<Memo>("Memo")
        memo?.let {
            viewModel.initViewModel(it)
        }
        Toast.makeText(this, memo.toString(), Toast.LENGTH_SHORT).show()
    }

}