package com.hegunhee.simplememoapp.presentation.Main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.hegunhee.simplememoapp.data.Dao.DataDao
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.databinding.ActivityMainBinding
import com.hegunhee.simplememoapp.presentation.BaseActivity
import com.hegunhee.simplememoapp.presentation.adapter.AccountItemViewAdapter
import com.hegunhee.simplememoapp.presentation.addMemo.AddMemoBetaActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MainActivity:  BaseActivity<MainViewModel,ActivityMainBinding>(){
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override val viewModel by viewModel<MainViewModel>()

    private val adapter = AccountItemViewAdapter()

    private lateinit var getResultText : ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initListener()
    }

    private fun initViews() = with(binding) {
        this.recyclerView.adapter = adapter
        getResultText = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            if(result.resultCode == RESULT_OK){
                val accountItem = result.data?.getParcelableExtra<accountItemEntity>(AddMemoBetaActivity.Item) ?: return@registerForActivityResult
                Toast.makeText(this@MainActivity, accountItem.toString(), Toast.LENGTH_SHORT).show()
                viewModel.addEntity(accountItem)
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
                handleSuccess(it)
            }
        }
    }

    fun handleSuccess(state : MainState.Success) = with(binding){
        adapter.setData(state.ItemList)
        adapter.notifyDataSetChanged()
        Log.d("data", state.ItemList.toString())
        val totalIncomeMoney = state.ItemList.filter { it.category.equals("수입") }.map { it.price }.sum()
        val totalSpendMoney = state.ItemList.filter { it.category.equals("지출") }.map { it.price }.sum()
        incomeTotalMoney.text = totalIncomeMoney.toString()
        spendTotalMoney.text = totalSpendMoney.toString()
        totalMoney.text = (totalIncomeMoney - totalSpendMoney).toString()
        incomeTotalMoney.setTextColor(Color.BLUE)
        spendTotalMoney.setTextColor(Color.RED)

    }

}