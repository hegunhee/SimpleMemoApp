package com.hegunhee.newsimplememoapp.ui.detailMemo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.ui.common.*
import com.hegunhee.newsimplememoapp.databinding.FragmentDetailMemoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate

@AndroidEntryPoint
class DetailMemoFragment : Fragment() {

    private val viewModel: DetailMemoViewModel by viewModels()
    private lateinit var binding: FragmentDetailMemoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail_memo, container, false)
        binding = FragmentDetailMemoBinding.bind(root).apply {
            this.viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        val memoId = navArgs<DetailMemoFragmentArgs>().value.memoId
        viewModel.initViewModel(memoId)
        observeData()
        return root
    }


    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.memoState.collect {
                        when (it) {
                            DetailMemoState.Back -> {
                                findNavController().popBackStack()
                            }
                            DetailMemoState.Update -> {
                                updateMemo()
                            }
                            DetailMemoState.SetAsset -> {
                                setAsset()
                            }
                            DetailMemoState.SetAttr -> {
                                setAttr()
                            }
                            DetailMemoState.SetDate -> {
                                setDate()
                            }
                            DetailMemoState.SetTime -> {
                                setTime()
                            }
                            DetailMemoState.Remove -> {
                                findNavController().popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateMemo() {
        with(viewModel) {
            if(price.value.isNullOrEmpty()){
                Toast.makeText(requireContext(), "가격을 설정해주세요", Toast.LENGTH_SHORT).show()
            }else {
                updateMemo()
                findNavController().popBackStack()
            }
        }
    }

    private fun setAsset() {
        AlertDialog.Builder(requireContext())
            .setTitle("자산")
            .setItems(
                assetArray,
                DialogInterface.OnClickListener { _, which ->
                    viewModel.setAsset(assetArray[which])
                }).create().show()
    }

    private fun setAttr() {
        val attrType = if (viewModel.category.value == "수입") {
            incomeAttr
        } else {
            expenseAttr
        }
        AlertDialog.Builder(requireContext())
            .setTitle("자산")
            .setItems(attrType,
                DialogInterface.OnClickListener { _, which ->
                    viewModel.setAttr(attrType[which])
                }).create().show()
    }

    private fun setDate() {
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            viewModel.setDate(LocalDate.of(year, month + 1, dayOfMonth))
        }.let { listener ->
            with(viewModel) {
                DatePickerDialog(requireContext(), listener, year, month - 1, day).show()
            }
        }

    }

    private fun setTime() {
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

        }.let { listener ->
            TimePickerDialog(
                requireContext(),
                listener,
                viewModel.hour,
                viewModel.minute,
                false
            ).show()
        }
    }
}