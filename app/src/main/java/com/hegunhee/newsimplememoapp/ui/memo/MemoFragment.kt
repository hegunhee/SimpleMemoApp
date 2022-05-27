package com.hegunhee.newsimplememoapp.ui.memo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.databinding.FragmentMemoBinding
import com.hegunhee.newsimplememoapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoFragment : BaseFragment<FragmentMemoBinding>(R.layout.fragment_memo) {

    private val viewModel : MemoViewModel by viewModels()
    private val adapter = MemoAdapter(arrayListOf()) { memo ->
        MemoFragmentDirections.memoToDetail(memo).also {
            findNavController().navigate(it)
        }
    }

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
            findNavController().navigate(R.id.memo_to_add)
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