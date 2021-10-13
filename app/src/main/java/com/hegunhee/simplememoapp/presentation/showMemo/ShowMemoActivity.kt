package com.hegunhee.simplememoapp.presentation.showMemo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.databinding.ShowMemoBinding
import com.hegunhee.simplememoapp.presentation.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowMemoActivity : BaseActivity<ShowMemoViewModel,ShowMemoBinding>(){
    override fun getViewBinding() = ShowMemoBinding.inflate(layoutInflater)

    override val viewModel by viewModel<ShowMemoViewModel>()

    override fun observeData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }
    private fun initViews() = with(binding){
        intent.getParcelableExtra<accountItemEntity>(item)?.let { item ->
            this.category.text = item.category
            this.asset.text = item.asset
            this.attr.text = item.attr
            this.day.text = item.day
            this.time.text = item.time
            this.price.setText(item.price.toString())
            this.description.setText(item.description ?: "")
        }

    }
    companion object {
        const val item = "item"
    }
}