package com.hegunhee.simplememoapp.presentation.Memo

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.databinding.FragmentMemoBinding
import com.hegunhee.simplememoapp.presentation.BaseFragment
import com.hegunhee.simplememoapp.presentation.adapter.AccountItemViewAdapter
import com.hegunhee.simplememoapp.presentation.adapter.callbackListener
import com.hegunhee.simplememoapp.presentation.addMemo.AddMemoBetaActivity
import com.hegunhee.simplememoapp.presentation.testMemo.TestMemoActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MemoFragment : BaseFragment<MemoViewModel, FragmentMemoBinding>(), callbackListener {
    override fun getViewBinding() = FragmentMemoBinding.inflate(layoutInflater)

    override val viewModel by viewModel<MemoViewModel>()

    private val adapter = AccountItemViewAdapter(this)

    companion object {
        fun newInstance() = MemoFragment()

        const val TAG = "MemoFragment"
    }

    override fun onResume() {
        super.onResume()
    }
    private lateinit var getResultText: ActivityResultLauncher<Intent>

    override fun initViews() = with(binding) {
        this.recyclerView.adapter = adapter
//        getResultText = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
//            if(result.resultCode == Activity.RESULT_OK){
//                val accountItem = result.data?.getParcelableExtra<accountItemEntity>(AddMemoBetaActivity.Item) ?: return@registerForActivityResult
//                Toast.makeText(requireContext(), accountItem.toString(), Toast.LENGTH_SHORT).show()
//                viewModel.addEntity(accountItem)
//            }
//        }
//        addMemo.setOnClickListener {
//            getResultText.launch(Intent(requireContext(),AddMemoBetaActivity::class.java))
//        }
        addMemo.setOnClickListener {
            //startActivityForResult()
            val intent = Intent(requireContext(), TestMemoActivity::class.java)
            intent.putExtra(TestMemoActivity.TYPE, TestMemoActivity.ADD)
            startActivity(intent)
        }
    }


    override fun observeData() = viewModel.liveData.observe(this) {
        when (it) {
            is MemoState.Uninitalized -> {
                Toast.makeText(requireContext(), "Uninitalized", Toast.LENGTH_SHORT).show()
            }
            is MemoState.Loading -> {
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            }
            is MemoState.Success -> {
                handleSuccess(it)
            }
        }
    }

    fun handleSuccess(state: MemoState.Success) = with(binding) {
        adapter.setData(state.ItemList)
        adapter.notifyDataSetChanged()
        Log.d("data", state.ItemList.toString())
        val totalIncomeMoney =
            state.ItemList.filter { it.category.equals("수입") }.map { it.price }.sum()
        val totalSpendMoney =
            state.ItemList.filter { it.category.equals("지출") }.map { it.price }.sum()
        incomeTotalMoney.text = totalIncomeMoney.toString()
        spendTotalMoney.text = totalSpendMoney.toString()
        totalMoney.text = (totalIncomeMoney - totalSpendMoney).toString()
        incomeTotalMoney.setTextColor(Color.BLUE)
        spendTotalMoney.setTextColor(Color.RED)

    }

    override fun onClick() {
        adapter.notifyDataSetChanged()
    }

}