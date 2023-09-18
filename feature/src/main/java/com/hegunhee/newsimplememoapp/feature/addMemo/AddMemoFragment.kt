package com.hegunhee.newsimplememoapp.feature.addMemo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hegunhee.newsimplememoapp.feature.R
import com.hegunhee.newsimplememoapp.feature.common.assetArray
import com.hegunhee.newsimplememoapp.feature.common.expenseAttr
import com.hegunhee.newsimplememoapp.feature.common.incomeAttr
import com.hegunhee.newsimplememoapp.feature.databinding.FragmentAddMemoBinding
import com.hegunhee.newsimplememoapp.feature.util.DateUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                            AddMemoState.SetPrice -> setPrice()
                        }
                    }
                }
            }
        }
    }
    private fun saveData() {
        findNavController().popBackStack()
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
            viewModel.setDate(year,month + 1,dayOfMonth)}.let { listener->
            with(viewModel){
                DatePickerDialog(requireContext(),listener,year,month-1,day).show()
            }
        }
    }

    private fun setTime() {
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            viewModel.setTime(hourOfDay,minute)
        }.let {
            TimePickerDialog(requireContext(),it,DateUtil.getHour(),DateUtil.getMinute(),false).show()
        }
    }

    private fun setPrice() {
        viewDataBinding.price.run {
            requestFocus()
            showKeyboard()
        }
    }

    private fun EditText.showKeyboard() {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(this,0)
    }
}