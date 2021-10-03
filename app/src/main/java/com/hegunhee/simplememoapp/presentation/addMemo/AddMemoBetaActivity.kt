package com.hegunhee.simplememoapp.presentation.addMemo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.hegunhee.simplememoapp.data.Entity.accountItem
import com.hegunhee.simplememoapp.databinding.AddMemoBetaBinding
import com.hegunhee.simplememoapp.presentation.Main.MainActivity
import java.time.LocalDate
import java.time.LocalTime

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
        income.setOnClickListener {
            category.text = "수입"
        }
        expenses.setOnClickListener {
            category.text = "지출"
        }
        save.setOnClickListener {
            Intent(this@AddMemoBetaActivity, MainActivity::class.java)?.apply {
                putExtra(
                    Item, accountItem(
                        category = "카테고리",
                        description = "설명",
                        day = "날짜",
                        time = "시간",
                        price = 20000,
                        payType = "카드"
                    )
                )
                setResult(RESULT_OK, this)
                finish()
            }
        }
        asset.setOnClickListener {
            Toast.makeText(this@AddMemoBetaActivity, "자산 테이블 호출.", Toast.LENGTH_SHORT).show()
            assetTable.isVisible = true

        }
        attr.setOnClickListener {
            Toast.makeText(this@AddMemoBetaActivity, "분류 테이블 호출", Toast.LENGTH_SHORT).show()
        }

        back.setOnClickListener {
            onBackPressed()
        }

        assetTableBackButton.setOnClickListener {
            assetTable.isGone = true
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
    }


    companion object {
        const val Item = "accountItem"
    }
}