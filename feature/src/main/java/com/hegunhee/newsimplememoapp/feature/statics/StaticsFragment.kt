package com.hegunhee.newsimplememoapp.feature.statics

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
import com.google.android.material.tabs.TabLayout
import com.hegunhee.newsimplememoapp.feature.R
import com.hegunhee.newsimplememoapp.feature.databinding.FragmentStaticsBinding
import com.hegunhee.newsimplememoapp.feature.dateDialog.DateDialogFragment
import com.hegunhee.newsimplememoapp.feature.memo.DateNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StaticsFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentStaticsBinding
    private val viewModel: StaticViewModel by viewModels()
    private val adapter = StaticsAdapter { statics ->
        val navArgs = StaticsNavArgs(
            attr = statics.attr,
            year = viewModel.yearDate.value,
            month = viewModel.monthDate.value
        )
        StaticsFragmentDirections.staticsToDetailStatics(navArgs).also {
            findNavController().navigate(it)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_statics, container, false)
        viewDataBinding = FragmentStaticsBinding.bind(root)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.apply {
            viewModel = this@StaticsFragment.viewModel
            lifecycleOwner = this@StaticsFragment
            recyclerview.adapter = adapter
        }
        viewDataBinding.categoryTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        viewModel.onIncomeTabClick()
                    }

                    1 -> {
                        viewModel.onExpenseTabClick()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        viewModel.initDate()
        observeData()
        fragmentResultListener()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.dateNavigation.collect { dateNavigation ->
                        when (dateNavigation) {
                            is DateNavigation.SelectDate -> {
                                DateDialogFragment.getInstance().show(childFragmentManager, DateDialogFragment.TAG)
                            }
                        }
                    }
                }
                launch {
                    viewModel.filteredStaticsData.collect{
                        adapter.setData(it)
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