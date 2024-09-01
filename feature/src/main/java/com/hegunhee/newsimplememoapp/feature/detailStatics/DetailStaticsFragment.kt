package com.hegunhee.newsimplememoapp.feature.detailStatics

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
import androidx.navigation.fragment.navArgs
import com.hegunhee.newsimplememoapp.feature.R
import com.hegunhee.newsimplememoapp.feature.common.memo.MemoServerAdapter
import com.hegunhee.newsimplememoapp.feature.databinding.FragmentDetailStaticsBinding
import com.hegunhee.newsimplememoapp.feature.dateDialog.DateDialogFragment
import com.hegunhee.newsimplememoapp.feature.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailStaticsFragment : Fragment() {

    private val viewModel: DetailStaticsViewModel by viewModels()
    private lateinit var binding: FragmentDetailStaticsBinding
    private lateinit var memoAdapter : MemoServerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail_statics,container,false)
        memoAdapter = MemoServerAdapter(viewModel)
        binding = FragmentDetailStaticsBinding.bind(root).apply {
            viewModel = this@DetailStaticsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            staticsRecyclerView.adapter = memoAdapter
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initData(navArgs<DetailStaticsFragmentArgs>().value.staticsArgs)
        observeData()
        fragmentResultListener()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.navigationEvent.collect {
                        when(it) {
                            is DetailStaticsNavigation.DateSelect -> {
                                DateDialogFragment.getInstance().show(childFragmentManager, DateDialogFragment.TAG)
                            }
                            is DetailStaticsNavigation.Back -> {
                                findNavController().popBackStack()
                            }
                            is DetailStaticsNavigation.DetailMemo -> {
                                MainFragmentDirections.memoToDetail(it.memoId).also { direction ->
                                    findNavController().navigate(direction)
                                }
                            }
                        }
                    }
                }
                launch {
                    viewModel.memoList.collect {
                        memoAdapter.submitList(it)
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