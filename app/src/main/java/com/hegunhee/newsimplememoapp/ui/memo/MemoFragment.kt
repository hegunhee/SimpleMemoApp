package com.hegunhee.newsimplememoapp.ui.memo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.data.entity.Memo
import com.hegunhee.newsimplememoapp.databinding.FragmentMemoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemoFragment : Fragment() {

    val viewModel: MemoViewModel by viewModel()
    private lateinit var binding: FragmentMemoBinding
    private val adapter = MemoAdapter(listOf())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_memo, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        initAdapter()
        observeData()
        viewModel.init_date()
    }


    private fun initAdapter() = with(binding) {
        recyclerview.adapter = adapter
    }

    private fun observeData() = viewModel.memoList.observe(viewLifecycleOwner) {
        when (it) {
            is MemoState.Uninitialized -> {
                Toast.makeText(requireContext(), "Uninitialized", Toast.LENGTH_SHORT).show()
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