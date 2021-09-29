package com.hegunhee.simplememoapp.presentation.addMemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hegunhee.simplememoapp.databinding.AddMemoBetaBinding

class AddMemoBetaActivity : AppCompatActivity() {

    private lateinit var binding : AddMemoBetaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddMemoBetaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }
    private fun initViews() = with(binding){

    }
}