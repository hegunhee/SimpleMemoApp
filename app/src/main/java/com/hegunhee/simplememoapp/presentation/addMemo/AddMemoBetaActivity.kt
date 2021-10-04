package com.hegunhee.simplememoapp.presentation.addMemo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.hegunhee.simplememoapp.data.Entity.accountItem
import com.hegunhee.simplememoapp.databinding.AddMemoBetaBinding
import com.hegunhee.simplememoapp.presentation.Main.MainActivity
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class AddMemoBetaActivity : AppCompatActivity() {

    private lateinit var binding: AddMemoBetaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddMemoBetaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() = with(binding) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            day.text = LocalDate.now().toString()
            time.text = LocalTime.now().toString()
        }

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
                return@setOnClickListener
            } else asset.text.toString()

            val attr = if (attr.text.isNullOrEmpty()) {
                Toast.makeText(this@AddMemoBetaActivity, "분류를 선택해주세요", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else attr.text.toString()
            val price = if (price.text.isNullOrEmpty()) {
                Toast.makeText(this@AddMemoBetaActivity, "가격을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else price.text.toString().toInt()
            val description = description.text.toString()

//            Toast.makeText(
//                this@AddMemoBetaActivity,
//                "category = $category day = $day time = $time asset = $asset attr = $attr price = $price desc = $description"
//                , Toast.LENGTH_SHORT
//            ).show()


            Intent(this@AddMemoBetaActivity, MainActivity::class.java)?.apply {
                putExtra(
                    Item, accountItem(
                        category,
                        day,
                        time,
                        asset,
                        attr,
                        price,
                        description
                    )
                )
                setResult(RESULT_OK, this)
                finish()
            }
        }

        day.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                this@AddMemoBetaActivity,
                DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
                    day.text = "$y:${m+1}:$d"
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DATE)
            ).show()
        }
        time.setOnClickListener {
            val cal = Calendar.getInstance()
            TimePickerDialog(
                this@AddMemoBetaActivity,
                TimePickerDialog.OnTimeSetListener { timePicker, h, m ->
                    time.text = "$h:$m"
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
            if (category.text == "수입")
                toggleTableVisiblity("attrIncome")
            else if (category.text == "지출")
                toggleTableVisiblity("attrExpenses")
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
    }
}