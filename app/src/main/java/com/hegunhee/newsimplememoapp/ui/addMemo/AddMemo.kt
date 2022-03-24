package com.hegunhee.newsimplememoapp.ui.addMemo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.data.entity.assetArray
import com.hegunhee.newsimplememoapp.data.entity.expenseAttr
import com.hegunhee.newsimplememoapp.data.entity.incomeAttr
import com.hegunhee.newsimplememoapp.databinding.ActivityAddMemoBinding
import org.koin.android.ext.android.inject
import java.time.LocalDate
import java.time.LocalDateTime


class AddMemo : AppCompatActivity() {

    private lateinit var binding: ActivityAddMemoBinding
    private val addMemoViewModel: AddMemoViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_memo)
        binding.viewmodel = addMemoViewModel
        binding.lifecycleOwner = this
        addMemoViewModel.initData()
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
        save.setOnClickListener {
            with(addMemoViewModel) {
                if (asset.value.isNullOrEmpty()) {
                    setAsset()
                } else if (attr.value.isNullOrEmpty()) {
                    setAttr()
                } else if (price.text.isNullOrEmpty()) {
                    Toast.makeText(this@AddMemo, "가격을 설정해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    val description = if (desc.text.isNullOrEmpty()) "" else desc.text.toString()
                    saveData(price.text.toString().toInt(), description)
                    finish()
                }
            }
        }

    }

    private fun setAsset() {
        AlertDialog.Builder(this)
            .setTitle("자산")
            .setItems(assetArray,
                DialogInterface.OnClickListener { dialogInterface, which ->
                    addMemoViewModel.asset.value = assetArray[which]
                }).create().show()
    }

    private fun setAttr() {
        val attrType = if (addMemoViewModel.category.value == "수입") {
            incomeAttr
        } else {
            expenseAttr
        }
        AlertDialog.Builder(this)
            .setTitle("자산")
            .setItems(attrType,
                DialogInterface.OnClickListener { dialogInterface, which ->
                    addMemoViewModel.attr.value = attrType[which]
                }).create().show()
    }

    private fun setDate() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            addMemoViewModel.setDate(LocalDate.of(year, month + 1, dayOfMonth))
        }

        with(addMemoViewModel) {
            DatePickerDialog(this@AddMemo, dateSetListener, year, month - 1, day).show()
        }

    }

    private fun setTime() {
        val time = LocalDateTime.now().plusHours(9)
        val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            with(addMemoViewModel) {
                if (hourOfDay > 12) {
                    ampm = "오후"
                    hour = hourOfDay - 12
                    this.minute = minute
                } else {
                    ampm = "오전"
                    hour = hourOfDay
                    this.minute = minute
                }
                Toast.makeText(this@AddMemo, "${ampm},${hour}:${minute}", Toast.LENGTH_SHORT).show()
                setTimeInfo()
            }

        }
        TimePickerDialog(this, timeSetListener, time.hour, time.minute, false).show()
    }
}