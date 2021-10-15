package com.hegunhee.simplememoapp.presentation.testMemo

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.databinding.TestMemoBinding
import com.hegunhee.simplememoapp.presentation.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestMemoActivity() : BaseActivity<TestMemoViewModel, TestMemoBinding>() {
    override fun getViewBinding() = TestMemoBinding.inflate(layoutInflater)

    override val viewModel by viewModel<TestMemoViewModel>()

    override fun observeData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        viewModel.type = intent.getStringExtra(TYPE).toString()
        if (viewModel.type == REPLACE) {
            Toast.makeText(this@TestMemoActivity, "고치기모드", Toast.LENGTH_SHORT).show()
            intent.getParcelableExtra<accountItemEntity>(ITEM)?.let { item ->
                viewModel.replaceModeData = item
                this.category.text = item.category
                this.asset.text = item.asset
                this.attr.text = item.attr
                this.day.text = item.day
                this.time.text = item.time
                this.price.setText(item.price.toString())
                this.description.setText(item.description ?: "")
            }
            this.save.isGone = true
            this.delete.isVisible = true
            this.replace.isVisible = true
        }
        if (viewModel.type == ADD) {
            Toast.makeText(this@TestMemoActivity, "더하기모드", Toast.LENGTH_SHORT).show()
            this.save.isVisible = true
            this.delete.isGone = true
            this.replace.isGone = true
        }

        this.delete.setOnClickListener {
            viewModel.deleteData()
            finish()
        }
        this.replace.setOnClickListener {
        }
    }

    companion object {
        const val TYPE = "type"
        const val ITEM = "item"
        const val REPLACE = "replace"
        const val ADD = "add"
    }

}