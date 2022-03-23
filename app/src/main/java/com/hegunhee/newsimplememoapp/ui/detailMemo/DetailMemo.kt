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
            testInitViews(it)
        }
        Toast.makeText(this, memo.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun testInitViews(memo : Memo) = with(binding){
        category.text = memo.category
        day.text = "${memo.year}/${memo.month}/${memo.day} (${memo.dayOfWeek})"
        time.text = "${memo.amPm} ${memo.hour}:${memo.minute}"
        asset.text = memo.asset
        attr.text = memo.attr

    }
}