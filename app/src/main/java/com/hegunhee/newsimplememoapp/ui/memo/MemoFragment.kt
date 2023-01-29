package com.hegunhee.newsimplememoapp.ui.memo

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
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.databinding.FragmentMemoBinding
import com.hegunhee.newsimplememoapp.ui.common.MemoAdapter
import com.hegunhee.newsimplememoapp.ui.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MemoFragment : Fragment() {

    private val viewModel : MemoViewModel by viewModels()
    private lateinit var viewDataBinding : FragmentMemoBinding

    private lateinit var adapter : MemoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_memo,container,false)
        adapter = MemoAdapter(viewModel)
        viewDataBinding = FragmentMemoBinding.bind(root).apply {
            viewModel = this@MemoFragment.viewModel
            recyclerview.adapter = adapter
            lifecycleOwner = viewLifecycleOwner
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        viewModel.initDate()
    }

    override fun onResume() {
        super.onResume()
        viewModel.initDate()
    }

    private fun observeData()  {
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                launch {
                    viewModel.memoList.collect {
                        adapter.submitList(it)
                    }
                }
                launch {
                    viewModel.memoNavigation.collect { memoNavigation ->
                        when(memoNavigation){
                            MemoNavigation.AddMemo -> {
                                findNavController().navigate(R.id.memo_to_add)
                            }
                            memoNavigation as MemoNavigation.DetailMemo-> {
                                MainFragmentDirections.memoToDetail(memoNavigation.memoId).also {
                                    findNavController().navigate(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}