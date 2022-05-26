package com.hegunhee.newsimplememoapp.ui.detailStatics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.databinding.ActivityDetailStaticsBinding
import com.hegunhee.newsimplememoapp.ui.detailMemo.DetailMemoActivity
import com.hegunhee.newsimplememoapp.ui.memo.MemoAdapter
import com.hegunhee.newsimplememoapp.ui.memo.MemoFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.viewModel

@AndroidEntryPoint
class DetailStaticsActivity : AppCompatActivity() {

    private val viewModel: DetaiStaticsViewModel by viewModels()
    private lateinit var binding: ActivityDetailStaticsBinding
    private val adapter = MemoAdapter(arrayListOf()) { memo ->
        Intent(this, DetailMemoActivity::class.java).apply {
            putExtra("Memo", memo)
            this@DetailStaticsActivity.startActivity(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_statics)
        binding.run {
            viewmodel = this@DetailStaticsActivity.viewModel
            lifecycleOwner = this@DetailStaticsActivity
            recyclerview.adapter = adapter
        }
        viewModel.initData(navArgs<DetailStaticsActivityArgs>().value.staticsData)

//        intent.getParcelableExtra<StaticsData>("statics")?.run {
//            Toast.makeText(this@DetailStaticsActivity, this.toString(), Toast.LENGTH_SHORT).show()
//            viewModel.initData(this)
//        }

        initObserver()
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.setData()
    }

    private fun initObserver() {
        initObserverData()
        initBackButtonObserve()
    }

    private fun initObserverData() = viewModel.detailStaticsState.observe(this) {
        when (it) {
            DetailStaticsState.Uninitialized -> {}
            is DetailStaticsState.Success -> {
                adapter.setData(it.data)
            }
            DetailStaticsState.NullOrEmpty -> {

            }
        }
    }

    private fun initBackButtonObserve() = viewModel.backButton.observe(this) {
        when (it) {
            BackButtonState.Back -> {
                onBackPressed()
            }
            BackButtonState.Uninitialized -> {
            }
        }
    }
}