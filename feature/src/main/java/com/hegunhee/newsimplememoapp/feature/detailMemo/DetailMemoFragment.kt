package com.hegunhee.newsimplememoapp.feature.detailMemo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hegunhee.newsimplememoapp.feature.R
import com.hegunhee.newsimplememoapp.feature.databinding.FragmentDetailMemoBinding
import com.hegunhee.newsimplememoapp.feature.detailCategory.DetailCategoryFragment
import com.hegunhee.newsimplememoapp.util.DateUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@AndroidEntryPoint
class DetailMemoFragment : Fragment() {

    private val viewModel: DetailMemoViewModel by viewModels()
    private lateinit var viewDataBinding: FragmentDetailMemoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail_memo, container, false)
        viewDataBinding = FragmentDetailMemoBinding.bind(root).apply {
            viewModel = this@DetailMemoFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        navArgs<DetailMemoFragmentArgs>().value.memoId.let {
            viewModel.initMemo(id = it)
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


    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.memoState.collect {
                        when (it) {
                            DetailMemoState.SetPrice -> {
                                focusPrice()
                            }
                            DetailMemoState.Update -> {
                                updateMemo()
                            }
                            DetailMemoState.Remove -> {
                                removeMemo()
                            }
                        }
                    }
                }
                launch {
                    viewModel.detailCategoryNavigation.collect {
                        DetailMemoFragmentDirections.detailMemoToDetailCategory(it.code).also { direction ->
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

    private fun updateMemo() {
        findNavController().popBackStack()
    }

    private fun removeMemo() {
        findNavController().popBackStack()
    }

    private fun setDate() {
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            viewModel.setDate(LocalDate.of(year, month + 1, dayOfMonth))
        }.let { listener ->
            viewModel.memoForm.value.memoDate.let {  memoDate ->
                DatePickerDialog(requireContext(),listener,memoDate.year,memoDate.monthValue -1,memoDate.dayOfMonth).show()
            }
        }

    }

    private fun setTime() {
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            viewModel.setTime(LocalTime.of(hourOfDay,minute,0))
        }.let {
            TimePickerDialog(requireContext(),it, DateUtil.getHour(), DateUtil.getMinute(),true).show()
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