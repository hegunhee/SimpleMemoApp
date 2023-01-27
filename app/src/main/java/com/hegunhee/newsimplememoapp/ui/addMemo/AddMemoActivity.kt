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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.data.entity.assetArray
import com.hegunhee.newsimplememoapp.data.entity.expenseAttr
import com.hegunhee.newsimplememoapp.data.entity.incomeAttr
import com.hegunhee.newsimplememoapp.databinding.ActivityAddMemoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@AndroidEntryPoint
class AddMemoActivity : AppCompatActivity() {

    private lateinit var viewDataBinding: ActivityAddMemoBinding
    private val viewModel : AddMemoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_memo)
        viewDataBinding.apply {
            viewModel = this@AddMemoActivity.viewModel
            lifecycleOwner = this@AddMemoActivity
        }
        viewModel.initData()
        observeData()
    }

    private fun observeData()  {
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.memoState.collect {
                        when(it){
                            AddMemoState.Back -> onBackPressed()
                            AddMemoState.Save -> saveData()
                            AddMemoState.SetAsset -> setAsset()
                            AddMemoState.SetAttr -> setAttr()
                            AddMemoState.SetDate -> setDate()
                            AddMemoState.SetTime -> setTime()
                        }
                    }
                }
            }
        }
    }

    private fun saveData() {
        with(viewDataBinding) {
            with(viewModel) {
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
            .setTitle(getString(R.string.asset))
            .setItems(assetArray,
                DialogInterface.OnClickListener { _, which ->
                    viewModel.setAsset(assetArray[which])
                }).create().show()
    }

    private fun setAttr() {
        val attrType = if (viewModel.category.value == "수입") {
            incomeAttr
        } else {
            expenseAttr
        }
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.attr))
            .setItems(attrType,
                DialogInterface.OnClickListener { _, which ->
                    viewModel.setAttr(attrType[which])
                }).create().show()
    }

    private fun setDate() {
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            viewModel.setDate(LocalDate.of(year, month + 1, dayOfMonth))}.let { listener->
            with(viewModel){
                DatePickerDialog(this@AddMemoActivity,listener,year,month-1,day).show()
            }
        }
    }

    private fun setTime() {
        val time = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
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
                setTimeInfo()
            }
        }.let {
            TimePickerDialog(this,it,time.hour,time.minute,false).show()
        }
    }
}