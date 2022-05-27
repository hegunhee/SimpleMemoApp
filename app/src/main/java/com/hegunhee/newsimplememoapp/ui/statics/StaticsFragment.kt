package com.hegunhee.newsimplememoapp.ui.statics

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.databinding.FragmentStaticsBinding
import com.hegunhee.newsimplememoapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class StaticsFragment : BaseFragment<FragmentStaticsBinding>(R.layout.fragment_statics) {

    private val viewModel : StaticViewModel by viewModels()
    private val adapter = StaticsAdapter { statics ->
        StaticsFragmentDirections.staticsToDetailStatics(statics).also {
            findNavController().navigate(it)
        }

    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            binding.apply {
                viewmodel = viewModel
                lifecycleOwner = this@StaticsFragment
                recyclerview.adapter = adapter
            }
            viewModel.initDate()
            initObserver()
        }

        override fun onResume() {
            super.onResume()
            viewModel.setData()
        }

        @SuppressLint("SetTextI18n")
        private fun initObserver() = viewModel.staticsData.observe(viewLifecycleOwner) {
            when (it) {
                StaticsState.Uninitialized -> {
                }
                is StaticsState.Success -> {
                    adapter.setData(it.list)
                }
                StaticsState.EmptyOrNull -> {
                }

            }
        }

        companion object {
        const val TAG = "statics"
        fun newInstance() = StaticsFragment()
    }
    }