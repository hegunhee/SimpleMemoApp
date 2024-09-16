package com.hegunhee.newsimplememoapp.feature.memo

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
import com.hegunhee.newsimplememoapp.feature.R
import com.hegunhee.newsimplememoapp.feature.common.memo.MemoAdapter
import com.hegunhee.newsimplememoapp.feature.databinding.FragmentMemoBinding
import com.hegunhee.newsimplememoapp.feature.dateDialog.DateDialogFragment
import com.hegunhee.newsimplememoapp.feature.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MemoFragment : Fragment() {

    private val viewModel : MemoViewModel by viewModels()
    private lateinit var viewDataBinding : FragmentMemoBinding

    private lateinit var adapter : MemoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_memo,container,false)
        adapter = MemoAdapter(viewModel)
        viewDataBinding = FragmentMemoBinding.bind(root).apply {
            viewModel = this@MemoFragment.viewModel
            memoRecyclerView.adapter = adapter
            lifecycleOwner = viewLifecycleOwner
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        fragmentResultListener()
    }

    private fun observeData()  {
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.memoList.collect { memos ->
                        adapter.submitList(memos)
                    }
                }
                launch {
                    viewModel.memoNavigation.collect { memoNavigation ->
                        when(memoNavigation){
                            is MemoNavigation.AddMemo -> {
                                findNavController().navigate(R.id.memo_to_add)
                            }
                            is MemoNavigation.DetailMemo -> {
                                MainFragmentDirections.memoToDetail(memoNavigation.memoId).also {
                                    findNavController().navigate(it)
                                }
                            }
                        }
                    }
                }
                launch {
                    viewModel.dateNavigation.collect{ dateNavigation ->
                        when(dateNavigation) {
                            is DateNavigation.SelectDate -> {
                                DateDialogFragment.getInstance().show(childFragmentManager,DateDialogFragment.TAG)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun fragmentResultListener() {
        childFragmentManager.setFragmentResultListener(DateDialogFragment.DATE_KEY,viewLifecycleOwner) { resultKey, result->
            when(resultKey) {
                DateDialogFragment.DATE_KEY -> {
                    val year = result.getInt(DateDialogFragment.YEAR_RESULT_KEY)
                    val month = result.getInt(DateDialogFragment.MONTH_RESULT_KEY)
                    viewModel.setDate(year,month)
                }
            }
        }
    }
}