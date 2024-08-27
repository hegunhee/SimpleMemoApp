package com.hegunhee.newsimplememoapp.feature.common.category

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType

@BindingAdapter("bind:categoryList","bind:actionHandler","bind:categoryType")
fun RecyclerView.setAdapter(categoryList : List<String>, actionHandler: CategoryActionHandler, categoryType: CategoryType) {
    val categoryAdapter = CategoryAdapter(actionHandler,categoryType)
    this.adapter = categoryAdapter
    categoryAdapter.submitList(categoryList)
}