package com.hegunhee.newsimplememoapp.ui.statics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.databinding.FragmentStaticsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class StaticsFragment : Fragment() {

    private lateinit var binding: FragmentStaticsBinding
    val viewModel: StaticViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statics, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        viewModel.initDate()
    }


    companion object {
        const val TAG = "statics"
        fun newInstance() = StaticsFragment()
    }
}