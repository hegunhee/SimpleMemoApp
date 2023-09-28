package com.hegunhee.newsimplememoapp.feature.addMemo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hegunhee.newsimplememoapp.feature.R
import com.hegunhee.newsimplememoapp.feature.databinding.FragmentAddMemoBinding
import com.hegunhee.newsimplememoapp.feature.detailCategory.DetailCategoryFragment
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
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setFragmentResultListener(DetailCategoryFragment.REFRESH_KEY) { key, bundle ->
            if(key == DetailCategoryFragment.REFRESH_KEY) {
                viewModel.refreshCategory()
            }
        }
    }

    private fun observeData()  {
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.memoState.collect {
                        when(it){
                            AddMemoState.Back -> findNavController().popBackStack()
                            AddMemoState.Save -> saveData()
                            AddMemoState.SetDate -> setDate()
                            AddMemoState.SetTime -> setTime()
                            AddMemoState.SetPrice -> setPrice()
                        }
                    }
                }
                launch {
                    viewModel.detailCategoryNavigation.collect {
                        AddMemoFragmentDirections.addMemoToDetailCategory(it.code).also { direction ->
                            findNavController().navigate(direction)
                        }
                    }
                }
            }
        }
    }
    private fun saveData() {
        findNavController().popBackStack()
    }

    private fun setDate() {
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            viewModel.setDate(year, month + 1, dayOfMonth)
        }.let { listener ->
            viewModel.dateInfo.value.let {  dateInfo ->
                DatePickerDialog(requireContext(),listener,dateInfo.year,dateInfo.month-1,dateInfo.day).show()
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