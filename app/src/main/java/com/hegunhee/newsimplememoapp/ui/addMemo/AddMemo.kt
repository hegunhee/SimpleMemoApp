package com.hegunhee.newsimplememoapp.ui.addMemo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hegunhee.newsimplememoapp.R
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

    private fun initListener() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.day.setOnClickListener {
            setDate()
        }
        binding.time.setOnClickListener {
            setTime()
        }
        binding.asset.setOnClickListener {
            Toast.makeText(this, "asset", Toast.LENGTH_SHORT).show()
        }
        binding.attr.setOnClickListener {
            Toast.makeText(this, "attr", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setDate(){
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year,month,dayOfMonth ->
            addMemoViewModel.setDate(LocalDate.of(year,month+1,dayOfMonth))
        }
        with(addMemoViewModel){
            DatePickerDialog(this@AddMemo,dateSetListener,year,month-1,day).show()
            Toast.makeText(this@AddMemo, "${year}년${month}월${day}일 ${day_of_week}", Toast.LENGTH_SHORT).show()
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