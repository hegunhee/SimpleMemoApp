package com.hegunhee.newsimplememoapp.feature.detailCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.model.category.toCategoryType
import com.hegunhee.newsimplememoapp.feature.R
import com.hegunhee.newsimplememoapp.feature.databinding.FragmentDetailCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailCategoryFragment() : Fragment() {

    private lateinit var viewDataBinding : FragmentDetailCategoryBinding
    private val viewModel : DetailCategoryViewModel by viewModels()
    private lateinit var categoryAdapter : DetailCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_category,container,false)
        categoryAdapter = DetailCategoryAdapter(viewModel)
        viewDataBinding = FragmentDetailCategoryBinding.bind(view).apply {
            viewModel = this@DetailCategoryFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            detailCategoryRecyclerView.adapter = categoryAdapter
        }
        fetchCategoryType(navArgs<DetailCategoryFragmentArgs>().value.categoryCode)
        return view
    }

    private fun fetchCategoryType(code : Int) {
        val categoryType = code.toCategoryType()
        if(categoryType == CategoryType.EMPTY){
            findNavController().popBackStack()
        }
        viewModel.setCategoryType(categoryType)
        viewDataBinding.categoryText.text = categoryType.subTitle
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionListener()
        observeData()
    }

    private fun actionListener() {
        viewDataBinding.addCategoryEditText.setOnEditorActionListener {editText, actionId, _ ->
            return@setOnEditorActionListener if(actionId == EditorInfo.IME_ACTION_DONE) {
                val text = editText.text.toString()
                if(text.isNotBlank()) {
                    viewModel.addCategory(editText.text.toString())
                }
                true
            } else {
                false
            }
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.categoryList.collect { categoryList ->
                        categoryAdapter.submitList(categoryList)
                    }
                }
                launch {
                    viewModel.categoryNavigation.collect {
                        when(it) {
                            DetailCategoryNavigation.Back -> {
                                findNavController().popBackStack()
                            }
                            DetailCategoryNavigation.Refresh -> {
                                setFragmentResult(REFRESH_KEY, bundleOf())
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {

        const val REFRESH_KEY = "refresh_key"
    }
}