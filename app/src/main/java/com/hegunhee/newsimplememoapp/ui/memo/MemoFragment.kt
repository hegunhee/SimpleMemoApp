package com.hegunhee.newsimplememoapp.ui.memo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.databinding.FragmentMemoBinding
import com.hegunhee.newsimplememoapp.ui.addMemo.AddMemo
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
            val intent = Intent(requireContext(), AddMemo::class.java)
            startActivity(intent)
        }
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