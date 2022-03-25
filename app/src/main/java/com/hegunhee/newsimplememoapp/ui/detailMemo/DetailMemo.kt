package com.hegunhee.newsimplememoapp.ui.detailMemo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.data.entity.Memo
import com.hegunhee.newsimplememoapp.data.entity.assetArray
import com.hegunhee.newsimplememoapp.data.entity.expenseAttr
import com.hegunhee.newsimplememoapp.data.entity.incomeAttr
import com.hegunhee.newsimplememoapp.databinding.ActivityDetailMemoBinding
import com.hegunhee.newsimplememoapp.ui.memo.MemoFragment
import org.koin.android.ext.android.inject
import java.time.LocalDate
import java.time.LocalDateTime

class DetailMemo : AppCompatActivity() {

    private val viewModel: DetailMemoViewModel by inject()
    private lateinit var binding: ActivityDetailMemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_memo)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        val memo = intent.getParcelableExtra<Memo>("Memo")
        memo?.let {
            viewModel.initViewModel(it)
        }
        initListener()
    }

    private fun initListener() = with(binding) {
        backButton.setOnClickListener {
            onBackPressed()
        }
        day.setOnClickListener {
            setDate()
        }
        time.setOnClickListener {
            setTime()
        }
        this.asset.setOnClickListener {
            setAsset()
        }
        attr.setOnClickListener {
            setAttr()
        }
        remove.setOnClickListener {
            viewModel.removeMemo()
            finish()
        }
        save.setOnClickListener {
            with(viewModel) {
                if (asset.value.isNullOrEmpty()) {
                    setAsset()
                } else if (attr.value.isNullOrEmpty()) {
                    setAttr()
                } else if (price.value.isNullOrEmpty()) {
                    Toast.makeText(this@DetailMemo, "가격을 설정해주세요", android.widget.Toast.LENGTH_SHORT)
                        .show()
                } else {
                    saveData()
                    finish()
                }
            }
        }
    }

    private fun setAsset() {
        AlertDialog.Builder(this)
            .setTitle("자산")
            .setItems(
                assetArray,
                DialogInterface.OnClickListener { dialogInterface, which ->
                    viewModel.asset.value = assetArray[which]
                }).create().show()
    }

    private fun setAttr() {
        val attrType = if (viewModel.category.value == "수입") {
            incomeAttr
        } else {
            expenseAttr
        }
        AlertDialog.Builder(this)
            .setTitle("자산")
            .setItems(attrType,
                DialogInterface.OnClickListener { dialogInterface, which ->
                    viewModel.attr.value = attrType[which]
                }).create().show()
    }

    private fun setDate() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            viewModel.setDate(LocalDate.of(year, month + 1, dayOfMonth))
        }

        with(viewModel) {
            DatePickerDialog(this@DetailMemo, dateSetListener, year, month - 1, day).show()
        }

    }

    private fun setTime() {
        val time = LocalDateTime.now().plusHours(9)
        val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            with(viewModel) {
                if (hourOfDay > 12) {
                    ampm = "오후"
                    hour = hourOfDay - 12
                    this.minute = minute
                } else {
                    ampm = "오전"
                    hour = hourOfDay
                    this.minute = minute
                }
                android.widget.Toast.makeText(
                    this@DetailMemo,
                    "${ampm},${hour}:${minute}",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                setTimeInfo()
            }

        }
        TimePickerDialog(this, timeSetListener, time.hour, time.minute, false).show()
    }

}