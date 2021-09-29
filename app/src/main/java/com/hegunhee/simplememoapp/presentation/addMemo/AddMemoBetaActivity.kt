package com.hegunhee.simplememoapp.presentation.addMemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hegunhee.simplememoapp.data.Entity.accountItem
import com.hegunhee.simplememoapp.databinding.AddMemoBetaBinding
import com.hegunhee.simplememoapp.presentation.Main.MainActivity

class AddMemoBetaActivity : AppCompatActivity() {

    private lateinit var binding : AddMemoBetaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddMemoBetaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }
    private fun initViews() = with(binding){
        save.setOnClickListener {
            Intent(this@AddMemoBetaActivity,MainActivity::class.java)?.apply {
                putExtra(Item,accountItem(
                    category = "카테고리",
                    description = "설명",
                    day = "날짜",
                    time = "시간",
                    price = 20000,
                    payType = "카드"
                ))
                setResult(RESULT_OK,this)
                finish()
            }
        }


    }

    companion object{
        const val Item = "accountItem"
    }
}