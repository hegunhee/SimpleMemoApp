package com.hegunhee.simplememoapp.presentation.testMemo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.hegunhee.simplememoapp.R
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.databinding.TestMemoBinding
import com.hegunhee.simplememoapp.presentation.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

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
            setCurrentTime()
        }

        this.delete.setOnClickListener {
            viewModel.deleteData()
            finish()
        }
        back.setOnClickListener {
            onBackPressed()
        }

        income.setOnClickListener {
            if (category.text == "수입") {
            } else {
                category.text = "수입"
                attr.text = ""
                save.setBackgroundColor(this@TestMemoActivity.resources.getColor(R.color.blue))
            }

        }
        expenses.setOnClickListener {
            if (category.text == "지출") {
            } else {
                category.text = "지출"
                attr.text = ""
                tableGroup.isGone = true
                save.setBackgroundColor(this@TestMemoActivity.resources.getColor(R.color.red))
            }

        }
        replace.setOnClickListener {
            val category = if (category.text.isNullOrEmpty()) {
                Toast.makeText(this@TestMemoActivity, "카테고리를 선택해주세요(수입/지출)", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else category.text.toString()

            val day = day.text.toString()
            val time = time.text.toString()

            val asset = if (asset.text.isNullOrEmpty()) {
                Toast.makeText(this@TestMemoActivity, "자산을 선택해주세요", Toast.LENGTH_SHORT)
                    .show()
                asset.callOnClick()
                return@setOnClickListener
            } else asset.text.toString()

            val attr = if (attr.text.isNullOrEmpty()) {
                Toast.makeText(this@TestMemoActivity, "분류를 선택해주세요", Toast.LENGTH_SHORT)
                    .show()
                attr.callOnClick()
                return@setOnClickListener
            } else attr.text.toString()

            val price = if (price.text.isNullOrEmpty()) {
                Toast.makeText(this@TestMemoActivity, "가격을 입력해주세요", Toast.LENGTH_SHORT).show()
                price.callOnClick()
                return@setOnClickListener
            } else price.text.toString().toInt()
            val description = description.text.toString()
            viewModel.replaceData(
                accountItemEntity(
                    category,
                    day,
                    time,
                    asset,
                    attr,
                    price,
                    description
                )
            )
            finish()
        }

        save.setOnClickListener {
            // 카테고리를 어떻게 불러와야할까?
            val category = if (category.text.isNullOrEmpty()) {
                Toast.makeText(this@TestMemoActivity, "카테고리를 선택해주세요(수입/지출)", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else category.text.toString()

            val day = day.text.toString()
            val time = time.text.toString()

            val asset = if (asset.text.isNullOrEmpty()) {
                Toast.makeText(this@TestMemoActivity, "자산을 선택해주세요", Toast.LENGTH_SHORT)
                    .show()
                asset.callOnClick()
                return@setOnClickListener
            } else asset.text.toString()

            val attr = if (attr.text.isNullOrEmpty()) {
                Toast.makeText(this@TestMemoActivity, "분류를 선택해주세요", Toast.LENGTH_SHORT)
                    .show()
                attr.callOnClick()
                return@setOnClickListener
            } else attr.text.toString()

            val price = if (price.text.isNullOrEmpty()) {
                Toast.makeText(this@TestMemoActivity, "가격을 입력해주세요", Toast.LENGTH_SHORT).show()
                price.callOnClick()
                return@setOnClickListener
            } else price.text.toString().toInt()
            val description = description.text.toString()

            viewModel.addData(
                accountItemEntity(
                    category,
                    day,
                    time,
                    asset,
                    attr,
                    price,
                    description
                )
            )
            finish()
//            Intent(this@TestMemoActivity, MemoFragment::class.java).apply {
//                val entity = accountItemEntity(category, day, time, asset, attr, price, description)
//                putExtra(AddMemoBetaActivity.Item, entity)
//                setResult(RESULT_OK, this)
//                finish()
//            }
        }

        day.setOnClickListener {
            DatePickerDialog(
                this@TestMemoActivity,
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
                this@TestMemoActivity,
                { timePicker, h, m ->
                    time.text = "${ampmTime(h)}:$m"
                },
                cal.get(Calendar.HOUR),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        asset.setOnClickListener {
            Toast.makeText(this@TestMemoActivity, "자산 테이블 호출.", Toast.LENGTH_SHORT).show()
            toggleTableVisiblity("asset")
        }

        attr.setOnClickListener {
            Toast.makeText(this@TestMemoActivity, "분류 테이블 호출", Toast.LENGTH_SHORT).show()
            if (category.text == "수입") {
                toggleTableVisiblity("attrIncome")
            } else if (category.text == "지출") {
                toggleTableVisiblity("attrExpenses")
            } else
                Toast.makeText(
                    this@TestMemoActivity,
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
            "${cal.get(Calendar.YEAR)}/${cal.get(Calendar.MONTH) + 1}/${
                cal.get(
                    Calendar.DAY_OF_MONTH
                )
            }(${getDefaultDayOfWeek()}) "
        day.text = dayText
        val timeText =
            if (cal.get(Calendar.HOUR_OF_DAY) > 12) "오후 ${
                cal.get(
                    Calendar.HOUR_OF_DAY
                ) - 12
            }:${
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
                Toast.makeText(this@TestMemoActivity, "assetTable 호출", Toast.LENGTH_SHORT).show()
                this.tableGroup.isGone = true
                this.assetTable.isVisible = true
            }
            "attrIncome" -> {
                Toast.makeText(this@TestMemoActivity, "attrIncomeTable 호출", Toast.LENGTH_SHORT)
                    .show()
                this.tableGroup.isGone = true
                this.attrIncomeTable.isVisible = true
            }
            "attrExpenses" -> {
                Toast.makeText(this@TestMemoActivity, "attrExpensesTable 호출", Toast.LENGTH_SHORT)
                    .show()
                this.tableGroup.isGone = true
                this.attrExpensesTable.isVisible = true
            }


        }
    }

    companion object {
        const val TYPE = "type"
        const val ITEM = "item"
        const val REPLACE = "replace"
        const val ADD = "add"
        val cal: Calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"))
    }

}