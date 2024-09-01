package com.hegunhee.newsimplememoapp.feature.detailCategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hegunhee.newsimplememoapp.feature.databinding.ItemDetailCategoryBinding

class DetailCategoryAdapter(private val actionHandler : DetailCategoryActionHandler)
    : ListAdapter<String,DetailCategoryAdapter.DetailCategoryViewHolder>(diffUtil) {

    inner class DetailCategoryViewHolder(private val binding : ItemDetailCategoryBinding) : ViewHolder(binding.root) {
        fun bindView(category : String) {
            binding.category = category
            binding.actionHandler = actionHandler
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailCategoryViewHolder {
        return DetailCategoryViewHolder(ItemDetailCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: DetailCategoryViewHolder, position: Int) {
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