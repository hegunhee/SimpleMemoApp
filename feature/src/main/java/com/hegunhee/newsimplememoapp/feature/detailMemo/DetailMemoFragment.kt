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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hegunhee.newsimplememoapp.feature.R
import com.hegunhee.newsimplememoapp.feature.databinding.FragmentDetailMemoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
            viewModel.initViewModel(memoId = it)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }


    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.memoState.collect {
                        when (it) {
                            DetailMemoState.Back -> {
                                findNavController().popBackStack()
                            }
                            DetailMemoState.Update -> {
                                updateMemo()
                            }
                            DetailMemoState.Remove -> {
                                removeMemo()
                            }
                            DetailMemoState.SetAsset -> {}
                            DetailMemoState.SetAttr -> {}
                            DetailMemoState.SetDate -> {
                                setDate()
                            }
                            DetailMemoState.SetTime -> {
                                setTime()
                            }
                            DetailMemoState.SetPrice -> {
                                setPrice()
                            }
                        }
                    }
                }
            }
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
            viewModel.setDate(year,month+1,dayOfMonth)
        }.let { listener ->
            viewModel.dateInfo.value.run {
                DatePickerDialog(requireContext(), listener, year, month - 1, day).show()
            }
        }

    }

    private fun setTime() {
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            viewModel.setTime(hourOfDay,minute)
        }.let { listener ->
            viewModel.timeInfo.value.let { timeInfo ->
                TimePickerDialog(requireContext(), listener, timeInfo.hour, timeInfo.minute, false).show()
            }
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