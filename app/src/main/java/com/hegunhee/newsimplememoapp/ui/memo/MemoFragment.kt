package com.hegunhee.newsimplememoapp.ui.memo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.databinding.FragmentMemoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MemoFragment : Fragment() {

    private val viewModel : MemoViewModel by viewModels()
    private lateinit var viewDataBinding : FragmentMemoBinding
    private val adapter = MemoAdapter(arrayListOf()) { memo ->
        MemoFragmentDirections.memoToDetail(memo).also {
            findNavController().navigate(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_memo,container,false)
        viewDataBinding = FragmentMemoBinding.bind(root).apply {
            viewModel = this@MemoFragment.viewModel
            recyclerview.adapter = adapter
            lifecycleOwner = viewLifecycleOwner
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
        viewModel.initDate()
    }

    override fun onResume() {
        super.onResume()
        viewModel.initDate()
    }

    private fun initViews() = with(viewDataBinding) {
        floatingButton.setOnClickListener {
            findNavController().navigate(R.id.memo_to_add)
        }
    }

    private fun observeData()  {
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.memoList.collect {
                    adapter.setData(it)
                }
            }
        }
    }


    companion object {
        fun newInstance() = MemoFragment()
        const val TAG = "MemoFragment"
    }
}