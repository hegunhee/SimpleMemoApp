package com.hegunhee.newsimplememoapp.ui.common

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hegunhee.newsimplememoapp.data.entity.MemoEntity
import com.hegunhee.newsimplememoapp.databinding.ItemMemoBinding


class MemoAdapter(val actionHandler : MemoAdapterActionHandler) :
    ListAdapter<MemoEntity, MemoAdapter.MemoViewHolder>(diffUtil) {

    inner class MemoViewHolder(private val binding: ItemMemoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(memoEntity: MemoEntity) = with(binding) {
            this.memo = memoEntity
            this.eventHandler = actionHandler
            day.text = memoEntity.day.toString()
            dayOfWeek.text = memoEntity.dayOfWeek
            amPm.text = memoEntity.amPm
            hour.text = memoEntity.hour.toString()
            minute.text = memoEntity.minute.toString()
            attr.text = memoEntity.attr
            description.text = if (memoEntity.description.isBlank()) {
                description.setTextColor(Color.GRAY)
                memoEntity.attr
            } else memoEntity.description
            asset.text = memoEntity.asset
            price.text = memoEntity.price.toString()
            if (memoEntity.category == "지출")
                price.setTextColor(Color.RED)
            else
                price.setTextColor(Color.BLUE)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }
}

internal object diffUtil : DiffUtil.ItemCallback<MemoEntity>(){
    override fun areItemsTheSame(oldItem: MemoEntity, newItem: MemoEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MemoEntity, newItem: MemoEntity): Boolean {
        return oldItem == newItem
    }

}