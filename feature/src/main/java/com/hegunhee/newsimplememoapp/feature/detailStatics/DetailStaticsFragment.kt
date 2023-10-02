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
import com.hegunhee.newsimplememoapp.feature.databinding.FragmentDetailStaticsBinding
import com.hegunhee.newsimplememoapp.feature.dateDialog.DateDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Adapter의 경우 다시
 */
@AndroidEntryPoint
class DetailStaticsFragment : Fragment() {

    private val viewModel: DetaiStaticsViewModel by viewModels()
    private lateinit var binding: FragmentDetailStaticsBinding
//    private val adapter = MemoAdapter() { memo ->
//        Intent(this, DetailMemoActivity::class.java).apply {
//            putExtra("Memo", memo)
//            this@DetailStaticsActivity.startActivity(this)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail_statics,container,false)
        binding = FragmentDetailStaticsBinding.bind(root).apply {
            viewModel = this@DetailStaticsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
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