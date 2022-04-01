package com.hegunhee.newsimplememoapp.ui.statics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.databinding.FragmentStaticsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class StaticsFragment : Fragment() {

    private lateinit var binding: FragmentStaticsBinding
    val viewModel: StaticViewModel by viewModel()
    private val adapter = StaticsAdapter()
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
        initObserver()
        initAdapter()
    }

    private fun initObserver() = viewModel.staticsData.observe(viewLifecycleOwner){
        when(it){
            StaticsState.Uninitialized -> {
                Toast.makeText(requireContext(), "uninitialized", Toast.LENGTH_SHORT).show()
            }
            is StaticsState.Success -> {
                Toast.makeText(requireContext(), it.list.toString(), Toast.LENGTH_SHORT).show()
                binding.total.isGone = false
                binding.recyclerview.isGone = false
                binding.empty.isGone = true
                binding.total.text = "합계 : ${it.list.sumOf { it.price }}원"
                adapter.setData(it.list)

            }
            StaticsState.EmptyOrNull ->{
                binding.total.isGone = true
                binding.recyclerview.isGone = true
                binding.empty.isGone = false
                Toast.makeText(requireContext(), "isEmpty", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun initAdapter(){
        binding.recyclerview.adapter = adapter
    }




    companion object {
        const val TAG = "statics"
        fun newInstance() = StaticsFragment()
    }
}