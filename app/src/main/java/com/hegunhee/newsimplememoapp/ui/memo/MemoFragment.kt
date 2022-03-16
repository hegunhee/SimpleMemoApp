package com.hegunhee.newsimplememoapp.ui.memo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.databinding.FragmentMemoBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemoFragment : Fragment() {

    val viewModel : MemoViewModel by inject()
    private lateinit var binding : FragmentMemoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_memo,container,false)
//        binding.viewmodel = viewModel
//        binding.lifecycleOwner = this
        initViews()
        return binding.root
    }

    private fun initViews() = with(binding){
        leftButton.setOnClickListener {
            Toast.makeText(context, "Click_left", Toast.LENGTH_SHORT).show()
        }
        rightButton.setOnClickListener {
            Toast.makeText(context, "Click_right", Toast.LENGTH_SHORT).show()
        }
    }


    companion object{
        fun newInstance() = MemoFragment()
        const val TAG = "MemoFragment"
    }
}