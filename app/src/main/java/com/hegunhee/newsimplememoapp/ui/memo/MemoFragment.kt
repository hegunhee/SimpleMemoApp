package com.hegunhee.newsimplememoapp.ui.memo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.databinding.FragmentMemoBinding
import com.hegunhee.newsimplememoapp.ui.BaseFragment
import com.hegunhee.newsimplememoapp.ui.addMemo.AddMemoActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemoFragment : BaseFragment<FragmentMemoBinding>(R.layout.fragment_memo) {

    val viewModel: MemoViewModel by viewModel()
    private val adapter = MemoAdapter(listOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("BaseTest","MemoFragmentOnViewCreated - ${R.layout.fragment_memo}")
        binding.viewmodel = viewModel
        initAdapter()
        initViews()
        observeData()
        viewModel.initDate()
    }

    override fun onResume() {
        super.onResume()
        viewModel.initDate()
    }


    private fun initAdapter() = with(binding) {
        recyclerview.adapter = adapter
    }

    private fun initViews() = with(binding) {
        floatingButton.setOnClickListener {
            val intent = Intent(requireContext(), AddMemoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeData() = viewModel.memoList.observe(viewLifecycleOwner) {
        when (it) {
            is MemoState.Uninitialized -> {
            }
            is MemoState.Success -> {
                binding.recyclerview.isVisible = true
                binding.emptyRecyclerview.isVisible = false
                adapter.setData(it.MemoList)
            }
            is MemoState.EmptyOrNull -> {
                binding.recyclerview.isVisible = false
                binding.emptyRecyclerview.isVisible = true
            }


        }

    }


    companion object {
        fun newInstance() = MemoFragment()
        const val TAG = "MemoFragment"
    }
}