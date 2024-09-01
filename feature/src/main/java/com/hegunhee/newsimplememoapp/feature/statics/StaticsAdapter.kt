package com.hegunhee.newsimplememoapp.feature.statics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hegunhee.newsimplememoapp.domain.model.memo.StaticsMemo
import com.hegunhee.newsimplememoapp.feature.databinding.ItemStaticsBinding

class StaticsAdapter(private val actionHandler : StaticsActionHandler) : ListAdapter<StaticsMemo,StaticsAdapter.StaticsViewHolder>(diffUtil) {

    inner class StaticsViewHolder(private val binding: ItemStaticsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(staticsMemo: StaticsMemo) = with(binding) {
            this.staticsMemo = staticsMemo
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
        private val diffUtil = object : DiffUtil.ItemCallback<StaticsMemo>() {
            override fun areItemsTheSame(oldItem: StaticsMemo, newItem: StaticsMemo): Boolean {
                return oldItem.attribute == newItem.attribute
            }

            override fun areContentsTheSame(oldItem: StaticsMemo, newItem: StaticsMemo): Boolean {
                return oldItem == newItem
            }

        }
    }
}