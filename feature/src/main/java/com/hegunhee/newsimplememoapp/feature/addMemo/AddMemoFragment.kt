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
import com.hegunhee.newsimplememoapp.util.DateUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

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
        clickListener()
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
                            AddMemoState.Save -> saveData()
                            AddMemoState.SetPrice -> focusPrice()
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

    private fun clickListener() {
        viewDataBinding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        viewDataBinding.day.setOnClickListener {
            setDate()
        }
        viewDataBinding.time.setOnClickListener {
            setTime()
        }
    }

    private fun saveData() {
        findNavController().popBackStack()
    }

    private fun setDate() {
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            viewModel.setDate(LocalDate.of (year, month + 1, dayOfMonth))
        }.let { listener ->
            viewModel.memoForm.value.memoDate.let {  memoDate ->
                DatePickerDialog(requireContext(),listener,memoDate.year,memoDate.monthValue,memoDate.dayOfMonth).show()
            }
        }
    }

    private fun setTime() {
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            viewModel.setTime(LocalTime.of(hourOfDay,minute,0))
        }.let {
            TimePickerDialog(requireContext(),it,DateUtil.getHour(),DateUtil.getMinute(),true).show()
        }
    }

    private fun focusPrice() {
        viewDataBinding.price.run {
            requestFocus()
            showKeyboard()
        }
    }

    private fun EditText.showKeyboard() {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(this,0)
    }
}