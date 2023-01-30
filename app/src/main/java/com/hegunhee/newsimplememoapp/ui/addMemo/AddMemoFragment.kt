package com.hegunhee.newsimplememoapp.ui.addMemo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.data.entity.assetArray
import com.hegunhee.newsimplememoapp.data.entity.expenseAttr
import com.hegunhee.newsimplememoapp.data.entity.incomeAttr
import com.hegunhee.newsimplememoapp.databinding.FragmentAddMemoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@AndroidEntryPoint
class AddMemoFragment : Fragment(){

    private lateinit var viewDataBinding: FragmentAddMemoBinding
    private val viewModel : AddMemoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add_memo,container,false)
        viewDataBinding = FragmentAddMemoBinding.bind(root).apply {
            viewModel = this@AddMemoFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        viewModel.initData()
        observeData()
        return root
    }

    private fun observeData()  {
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.memoState.collect {
                        when(it){
                            AddMemoState.Back -> findNavController().popBackStack()
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
        with(viewDataBinding){
            if(price.text.isNullOrEmpty()){
                Toast.makeText(requireContext(), "가격을 설정해주세요", Toast.LENGTH_SHORT).show()
            }else{
                val description = if (desc.text.isNullOrEmpty()) "" else desc.text.toString()
                this@AddMemoFragment.viewModel.saveData(price.text.toString().toInt(),description)
                findNavController().popBackStack()
            }
        }
    }

    private fun setAsset() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.asset))
            .setItems(
                assetArray,
                DialogInterface.OnClickListener { _, which ->
                    viewModel.setAsset(assetArray[which])
                }).create().show()
    }

    private fun setAttr() {
        val attrType = if (viewModel.category.value == getString(R.string.income)) {
            incomeAttr
        } else {
            expenseAttr
        }
        AlertDialog.Builder(requireContext())
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
                DatePickerDialog(requireContext(),listener,year,month-1,day).show()
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
            TimePickerDialog(requireContext(),it,time.hour,time.minute,false).show()
        }
    }
}