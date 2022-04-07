package com.hegunhee.newsimplememoapp.ui.detailStatics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.databinding.ActivityDetailStaticsBinding
import com.hegunhee.newsimplememoapp.ui.memo.MemoAdapter
import com.hegunhee.newsimplememoapp.ui.statics.StaticsData
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailStaticsActivity : AppCompatActivity() {

    private val viewModel : DetaiStaticsViewModel by viewModel()
    private lateinit var binding : ActivityDetailStaticsBinding
    private val adapter = MemoAdapter(listOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_statics)
        binding.run {
            viewmodel = this@DetailStaticsActivity.viewModel
            lifecycleOwner = this@DetailStaticsActivity
            recyclerview.adapter = adapter
        }
        intent.getParcelableExtra<StaticsData>("statics")?.run {
            Toast.makeText(this@DetailStaticsActivity, this.toString(), Toast.LENGTH_SHORT).show()
            viewModel.initData(this)
        }
        initObserver()
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.setData()
    }

    private fun initObserver(){
        initObserverData()
        initBackButtonObserve()
    }
    private fun initObserverData() = viewModel.detailStaticsState.observe(this){
        when(it){
            DetailStaticsState.Uninitialized -> {}
            is DetailStaticsState.Success -> {
                adapter.setData(it.data)
            }
            DetailStaticsState.NullOrEmpty -> {

            }
        }
    }
    private fun initBackButtonObserve() = viewModel.backButton.observe(this){
        when(it){
            BackButtonState.Back -> {onBackPressed()}
            BackButtonState.Uninitialized -> {
            }
        }
    }
}