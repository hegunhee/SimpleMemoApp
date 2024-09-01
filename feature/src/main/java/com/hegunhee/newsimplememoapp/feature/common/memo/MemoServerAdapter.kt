package com.hegunhee.newsimplememoapp.feature.common.memo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hegunhee.newsimplememoapp.feature.R
import com.hegunhee.newsimplememoapp.feature.common.MemoAdapterActionHandler
import com.hegunhee.newsimplememoapp.feature.databinding.ItemServerMemoBinding
import com.hegunhee.newsimplememoapp.feature.databinding.ItemServerMemoDateBinding

class MemoServerAdapter(val actionHandler : MemoAdapterActionHandler) :
    ListAdapter<MemoType,MemoServerAdapter.MemoAdapterViewHolder>(diffUtil){

    sealed class MemoAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bindView(memo: MemoType)

    }

    inner class MemoDateViewHolder(private val binding: ItemServerMemoDateBinding) : MemoAdapterViewHolder(binding.root) {
        override fun bindView(memo: MemoType): Unit = with(binding) {
            val memoDate = memo as MemoType.MemoDate
            this.memoDate = memoDate
        }

    }

    inner class MemoViewHolder(private val binding: ItemServerMemoBinding) : MemoAdapterViewHolder(binding.root) {
        override fun bindView(memo: MemoType): Unit = with(binding) {
            val memoEntity = memo as MemoType.Memo
            this.memo = memoEntity
            this.eventHandler = actionHandler
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoAdapterViewHolder {
        return when(viewType) {
            R.layout.item_server_memo ->{
                MemoViewHolder(ItemServerMemoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            R.layout.item_server_memo_date -> {
                MemoDateViewHolder(ItemServerMemoDateBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            else -> { throw IllegalArgumentException()}
        }
    }

    override fun onBindViewHolder(holder: MemoAdapterViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MemoType.Memo -> R.layout.item_server_memo
            is MemoType.MemoDate -> R.layout.item_server_memo_date
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MemoType>() {
            override fun areItemsTheSame(oldItem: MemoType, newItem: MemoType): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MemoType, newItem: MemoType): Boolean {
                return oldItem == newItem
            }
        }
    }
}