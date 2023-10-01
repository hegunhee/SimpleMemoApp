package com.hegunhee.newsimplememoapp.feature.statics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hegunhee.newsimplememoapp.domain.model.StaticsData
import com.hegunhee.newsimplememoapp.feature.databinding.ItemStaticsBinding

class StaticsAdapter(private val actionHandler : StaticsActionHandler) : ListAdapter<StaticsData,StaticsAdapter.StaticsViewHolder>(diffUtil) {

    inner class StaticsViewHolder(private val binding: ItemStaticsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(statics: StaticsData) = with(binding) {
            staticsData = statics
            actionHandler = this@StaticsAdapter.actionHandler
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaticsViewHolder {
        val binding = ItemStaticsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StaticsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StaticsViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    companion object{
        private val diffUtil = object : DiffUtil.ItemCallback<StaticsData>() {
            override fun areItemsTheSame(oldItem: StaticsData, newItem: StaticsData): Boolean {
                return oldItem.attr == newItem.attr
            }

            override fun areContentsTheSame(oldItem: StaticsData, newItem: StaticsData): Boolean {
                return oldItem == newItem
            }

        }
    }
}