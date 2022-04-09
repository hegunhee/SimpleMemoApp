package com.hegunhee.newsimplememoapp.ui.memo

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.databinding.FragmentMemoBinding
import com.hegunhee.newsimplememoapp.ui.BaseFragment
import com.hegunhee.newsimplememoapp.ui.addMemo.AddMemoActivity
import com.hegunhee.newsimplememoapp.ui.detailMemo.DetailMemoActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemoFragment : BaseFragment<FragmentMemoBinding>(R.layout.fragment_memo) {

    val viewModel: MemoViewModel by viewModel()
    private val adapter = MemoAdapter(listOf()) { memo ->
        Intent(requireContext(), DetailMemoActivity::class.java).apply {
            putExtra("Memo", memo)
            requireContext().startActivity(this)
        }}
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            binding.apply {
                viewmodel = viewModel
                recyclerview.adapter = adapter
            }
            initViews()
            observeData()
            viewModel.initDate()
        }

        override fun onResume() {
            super.onResume()
            viewModel.initDate()
        }

        private fun initViews() = with(binding) {
            floatingButton.setOnClickListener {
                Intent(requireContext(), AddMemoActivity::class.java).apply { startActivity(this) }
            }
        }

        private fun observeData() = viewModel.memoList.observe(viewLifecycleOwner) {
            when (it) {
                is MemoState.Uninitialized -> {
                }
                is MemoState.Success -> {
                    adapter.setData(it.MemoList)
                }
                is MemoState.EmptyOrNull -> {
                }
            }
        }


        companion object {
        fun newInstance() = MemoFragment()
        const val TAG = "MemoFragment"
    }
    }