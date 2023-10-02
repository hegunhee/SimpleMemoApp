package com.hegunhee.newsimplememoapp.feature.detailStatics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hegunhee.newsimplememoapp.feature.R
import com.hegunhee.newsimplememoapp.feature.databinding.FragmentDetailStaticsBinding
import dagger.hilt.android.AndroidEntryPoint

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
        initObserver()
    }

    private fun initObserver() {
        initObserverData()
        initBackButtonObserve()
    }

    private fun initObserverData() = viewModel.detailStaticsState.observe(this) {
//        when (it) {
//            DetailStaticsState.Uninitialized -> {}
//            is DetailStaticsState.Success -> {
//                adapter.submitList(it.data)
//            }
//            DetailStaticsState.NullOrEmpty -> {
//
//            }
//        }
    }

    private fun initBackButtonObserve() = viewModel.backButton.observe(this) {
        when (it) {
            BackButtonState.Back -> {
                findNavController().popBackStack()
            }
            BackButtonState.Uninitialized -> {}
        }
    }
}