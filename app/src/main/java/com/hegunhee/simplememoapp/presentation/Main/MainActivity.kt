package com.hegunhee.simplememoapp.presentation.Main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.hegunhee.simplememoapp.data.Dao.DataDao
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.databinding.ActivityMainBinding
import com.hegunhee.simplememoapp.presentation.BaseActivity
import com.hegunhee.simplememoapp.presentation.addMemo.AddMemoBetaActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MainActivity:  BaseActivity<MainViewModel,ActivityMainBinding>(){
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override val viewModel by viewModel<MainViewModel>()

    private lateinit var getResultText : ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initListener()
    }

    private fun initViews() {
        getResultText = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            if(result.resultCode == RESULT_OK){
                val accountItem = result.data?.getParcelableExtra<accountItemEntity>(AddMemoBetaActivity.Item)
                Toast.makeText(this, accountItem.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initListener() = with(binding){
        addMemo.setOnClickListener {
            getResultText.launch(Intent(this@MainActivity,AddMemoBetaActivity::class.java))
        }
    }

    override fun observeData() = viewModel.liveData.observe(this){
        when(it){
            is MainState.Uninitalized ->{
                Toast.makeText(this, "Uninitalized", Toast.LENGTH_SHORT).show()
            }
            is MainState.Loading -> {
                Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
            }
            is MainState.Success -> {
                Log.d("data", it.ItemList.toString())
            }
        }
    }

}