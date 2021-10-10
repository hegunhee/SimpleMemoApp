package com.hegunhee.simplememoapp.presentation.Statis

import com.hegunhee.simplememoapp.databinding.FragmentStatisBinding
import com.hegunhee.simplememoapp.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatisFragment : BaseFragment<StatisViewModel, FragmentStatisBinding>() {

    companion object{
        fun newInstance() = StatisFragment()
        const val TAG = "StatisFragment"
    }
    override fun getViewBinding() = FragmentStatisBinding.inflate(layoutInflater)

    override fun observeData() {
    }

    override fun initViews() = with(binding) {
        this.testTextView.text = "왔읍니다"
    }


    override val viewModel by viewModel<StatisViewModel>()
}