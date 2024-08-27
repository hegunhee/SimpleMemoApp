package com.hegunhee.newsimplememoapp.feature.common.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.feature.databinding.ItemCategoryBinding

class CategoryAdapter(val actionHandler: CategoryActionHandler, val categoryType : CategoryType) : ListAdapter<String, CategoryAdapter.CategoryViewHolder>(
    diffUtil
) {

    inner class CategoryViewHolder(private val binding : ItemCategoryBinding) : ViewHolder(binding.root) {

        fun bindView(category : String) {
            binding.category = category
            binding.categoryType = categoryType
            binding.actionHandler = actionHandler
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    companion object{
        private val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

}