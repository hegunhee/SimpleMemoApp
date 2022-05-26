package com.hegunhee.newsimplememoapp.ui.addMemo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.data.entity.assetArray
import com.hegunhee.newsimplememoapp.data.entity.expenseAttr
import com.hegunhee.newsimplememoapp.data.entity.incomeAttr
import com.hegunhee.newsimplememoapp.databinding.ActivityAddMemoBinding
import dagger.hilt.android.AndroidEntryPoint
import org.koin.android.ext.android.inject
import java.time.LocalDate
import java.time.LocalDateTime

@AndroidEntryPoint
class AddMemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMemoBinding
//    private val addMemoViewModel: AddMemoViewModel by inject()
    private val addMemoViewModel : AddMemoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_memo)
        binding.apply {
            viewmodel = addMemoViewModel
            lifecycleOwner = this@AddMemoActivity
        }
        addMemoViewModel.initData()
        observeData()
    }

    private fun observeData() = addMemoViewModel.memoState.observe(this) {
        when (it) {
            AddMemoState.Uninitialized -> {
            }
            AddMemoState.Back -> {
                onBackPressed()
            }
            AddMemoState.Save -> {
                saveData()
            }
            AddMemoState.SetAsset -> {
                setAsset()
            }
            AddMemoState.SetAttr -> {
                setAttr()
            }
            AddMemoState.SetDate -> {
                setDate()
            }
            AddMemoState.SetTime -> {
                setTime()
            }
        }
    }

    private fun saveData() {
        with(binding) {
            with(addMemoViewModel) {
                if (asset.value.isNullOrEmpty()) {
                    setAsset()
                } else if (attr.value.isNullOrEmpty()) {
                    setAttr()
                } else if (price.text.isNullOrEmpty()) {
                    Toast.makeText(this@AddMemoActivity, "가격을 설정해주세요", Toast.LENGTH_SHORT).show()
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

        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            addMemoViewModel.setDate(LocalDate.of(year, month + 1, dayOfMonth))}.let {  listener->
            with(addMemoViewModel){
                DatePickerDialog(this@AddMemoActivity,listener,year,month-1,day).show()
            }
        }

    }

    private fun setTime() {
        val time = LocalDateTime.now().plusHours(9)
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
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
                setTimeInfo()
            }
        }.let {
            TimePickerDialog(this,it,time.hour,time.minute,false).show()
        }
    }
}