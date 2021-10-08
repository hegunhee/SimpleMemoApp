package com.hegunhee.simplememoapp.presentation.addMemo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.hegunhee.simplememoapp.R
import com.hegunhee.simplememoapp.data.Dao.DataDao
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.databinding.AddMemoBetaBinding
import com.hegunhee.simplememoapp.presentation.Main.MainActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class AddMemoBetaActivity() : AppCompatActivity() {

    private lateinit var binding: AddMemoBetaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddMemoBetaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() = with(binding) {
        setCurrentTime()
        back.setOnClickListener {
            onBackPressed()
        }

        income.setOnClickListener {
            if (category.text == "수입") {
            } else {
                category.text = "수입"
                attr.text = ""
                tableGroup.isGone = true
            }

        }
        expenses.setOnClickListener {
            if (category.text == "지출") {
            } else {
                category.text = "지출"
                attr.text = ""
                tableGroup.isGone = true
            }
        }

        save.setOnClickListener {
            // 카테고리를 어떻게 불러와야할까?
            val category = if (category.text.isNullOrEmpty()) {
                Toast.makeText(this@AddMemoBetaActivity, "카테고리를 선택해주세요(수입/지출)", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else category.text.toString()

            val day = day.text.toString()
            val time = time.text.toString()

            val asset = if (asset.text.isNullOrEmpty()) {
                Toast.makeText(this@AddMemoBetaActivity, "자산을 선택해주세요", Toast.LENGTH_SHORT)
                    .show()
                asset.callOnClick()
                return@setOnClickListener
            } else asset.text.toString()

            val attr = if (attr.text.isNullOrEmpty()) {
                Toast.makeText(this@AddMemoBetaActivity, "분류를 선택해주세요", Toast.LENGTH_SHORT)
                    .show()
                attr.callOnClick()
                return@setOnClickListener
            } else attr.text.toString()

            val price = if (price.text.isNullOrEmpty()) {
                Toast.makeText(this@AddMemoBetaActivity, "가격을 입력해주세요", Toast.LENGTH_SHORT).show()
                price.callOnClick()
                return@setOnClickListener
            } else price.text.toString().toInt()
            val description = description.text.toString()

            Intent(this@AddMemoBetaActivity, MainActivity::class.java).apply {
                val entity = accountItemEntity(category, day, time, asset, attr, price, description)
                putExtra(Item, entity)
                setResult(RESULT_OK, this)
                finish()
            }
        }

        day.setOnClickListener {
            DatePickerDialog(
                this@AddMemoBetaActivity,
                { datePicker, y, m, d ->
                    day.text = "${y}/${m + 1}/$d (${getDayOfWeek("$y-${m + 1}-${d}")})"
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DATE)
            ).show()
        }
        time.setOnClickListener {
            TimePickerDialog(
                this@AddMemoBetaActivity,
                { timePicker, h, m ->
                    time.text = "${ampmTime(h)}:$m"
                },
                cal.get(Calendar.HOUR),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        asset.setOnClickListener {
            Toast.makeText(this@AddMemoBetaActivity, "자산 테이블 호출.", Toast.LENGTH_SHORT).show()
            toggleTableVisiblity("asset")
        }

        attr.setOnClickListener {
            Toast.makeText(this@AddMemoBetaActivity, "분류 테이블 호출", Toast.LENGTH_SHORT).show()
            if (category.text == "수입") {
                toggleTableVisiblity("attrIncome")
            }
            else if (category.text == "지출") {
                toggleTableVisiblity("attrExpenses")
            }
            else
                Toast.makeText(
                    this@AddMemoBetaActivity,
                    "카테고리를 선택해주세요(수입 or 지출)",
                    Toast.LENGTH_SHORT
                ).show()
        }

        assetCard.setOnClickListener {
            asset.text = "카드"
        }
        assetCash.setOnClickListener {
            asset.text = "현금"
        }
        assetKakaoBank.setOnClickListener {
            asset.text = "카카오뱅크"
        }

        attrIncomeSalary.setOnClickListener {
            attr.text = "월급"
        }
        attrIncomeExtraIncome.setOnClickListener {
            attr.text = "부수입"
        }
        attrIncomePettyCash.setOnClickListener {
            attr.text = "용돈"
        }

        attrExpensesEat.setOnClickListener {
            attr.text = "식비"
        }
        attrExpensesTransCost.setOnClickListener {
            attr.text = "교통/차량"
        }
        attrExpensesCulture.setOnClickListener {
            attr.text = "문화생활"
        }

        assetTableBackButton.setOnClickListener {
            assetTable.isGone = true
        }
        attrIncomeTableBackButton.setOnClickListener {
            attrIncomeTable.isGone = true
        }
        attrExpensesTableBackButton.setOnClickListener {
            attrExpensesTable.isGone = true
        }

    }

    private fun setCurrentTime() = with(binding) {
        val dayText =
            "${cal.get(Calendar.YEAR)}/${cal.get(Calendar.MONTH) + 1}/${cal.get(Calendar.DAY_OF_MONTH)}(${getDefaultDayOfWeek()}) "
        day.text = dayText
        val timeText =
            if (cal.get(Calendar.HOUR_OF_DAY) > 12) "오후 ${cal.get(Calendar.HOUR_OF_DAY) - 12}:${
                cal.get(
                    Calendar.MINUTE
                )
            }" else "오전 ${
                cal.get(Calendar.HOUR_OF_DAY)
            }:${cal.get(Calendar.MINUTE)}"
        time.text = timeText
    }

    private fun ampmTime(time: Int): String {
        return if (time > 12) {
            "오후 ${time - 12}"
        } else {
            "오전 $time"
        }
    }

    private fun getDayOfWeek(date: String): String {
        val df = SimpleDateFormat("yyyy-MM-dd")
        val nDate = df.parse(date)
        val specificCal = Calendar.getInstance().apply { this.time = nDate }
        return getDefaultDayOfWeek(specificCal)
    }

    private fun getDefaultDayOfWeek(calender: Calendar = cal): String {
        Log.d("Default", "" + calender.time)
        return when (calender.get(Calendar.DAY_OF_WEEK)) {
            1 -> "일"
            2 -> "월"
            3 -> "화"
            4 -> "수"
            5 -> "목"
            6 -> "금"
            7 -> "토"
            else -> "null"
        }
    }

    private fun toggleTableVisiblity(category: String) = with(binding) {
        when (category) {
            "asset" -> {
                Toast.makeText(this@AddMemoBetaActivity, "assetTable 호출", Toast.LENGTH_SHORT).show()
                tableGroup.isGone = true
                assetTable.isVisible = true
            }
            "attrIncome" -> {
                Toast.makeText(this@AddMemoBetaActivity, "attrIncomeTable 호출", Toast.LENGTH_SHORT)
                    .show()
                tableGroup.isGone = true
                attrIncomeTable.isVisible = true
            }
            "attrExpenses" -> {
                Toast.makeText(this@AddMemoBetaActivity, "attrExpensesTable 호출", Toast.LENGTH_SHORT)
                    .show()
                tableGroup.isGone = true
                attrExpensesTable.isVisible = true
            }


        }
    }


    companion object {
        const val Item = "accountItem"
        val cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"))
    }
}