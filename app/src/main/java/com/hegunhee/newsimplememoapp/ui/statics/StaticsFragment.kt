package com.hegunhee.newsimplememoapp.ui.statics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.databinding.FragmentStaticsBinding


class StaticsFragment : Fragment() {

    private lateinit var binding : FragmentStaticsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_statics,container,false)
        return binding.root
    }

    companion object{
        const val TAG = "statics"
        fun newInstance() = StaticsFragment()
    }
}