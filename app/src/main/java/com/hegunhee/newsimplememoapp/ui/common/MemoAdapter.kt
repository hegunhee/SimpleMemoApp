package com.hegunhee.newsimplememoapp.ui.common

import android.graphics.Color
import android.os.Debug.MemoryInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hegunhee.newsimplememoapp.R
import com.hegunhee.newsimplememoapp.data.entity.MemoEntity
import com.hegunhee.newsimplememoapp.data.entity.toMemoEntity
import com.hegunhee.newsimplememoapp.databinding.ItemMemoBinding
import com.hegunhee.newsimplememoapp.databinding.ItemMemoDateBinding
import com.hegunhee.newsimplememoapp.domain.model.MemoType


class MemoAdapter(val actionHandler : MemoAdapterActionHandler) :
    ListAdapter<MemoType, MemoAdapter.MemoAdapterViewHolder>(diffUtil) {

    sealed class MemoAdapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bindView(memo: MemoType)

    }

    inner class MemoDateViewHolder(private val binding : ItemMemoDateBinding) : MemoAdapterViewHolder(binding.root){

        override fun bindView(memo : MemoType) : Unit = with(binding){
            val memoDate = memo as MemoType.MemoDate
            this.day.text = memoDate.day.toString()
            this.dayOfWeek.text = memoDate.dayOfWeek +"요일"
            this.yearAndMonth.text = "" + memoDate.year + "." + memoDate.month
            this.income.text = memoDate.incomeSum.toString() + "원"
            this.expense.text = memoDate.expenseSum.toString() + "원"
        }

    }

    inner class MemoViewHolder(private val binding: ItemMemoBinding) : MemoAdapterViewHolder(binding.root){

        override fun bindView(memo: MemoType) : Unit = with(binding) {
            val memoEntity = memo as MemoType.Memo
            this.memo = memoEntity.toMemoEntity()
            this.eventHandler = actionHandler
            attr.text = memoEntity.attr
            desc.text = memoEntity.description
            time.text = memoEntity.amPm + " " + memoEntity.hour +":" + memoEntity.minute
            price.text = memoEntity.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoAdapterViewHolder {
        return when(viewType) {
            R.layout.item_memo ->{
                MemoViewHolder(ItemMemoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            R.layout.item_memo_date -> {
                MemoDateViewHolder(ItemMemoDateBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            else -> { throw IllegalArgumentException()}

        }
    }

    override fun onBindViewHolder(holder: MemoAdapterViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)){
            is MemoType.Memo -> R.layout.item_memo
            is MemoType.MemoDate -> R.layout.item_memo_date
        }
    }
}

internal object diffUtil : DiffUtil.ItemCallback<MemoType>(){
    override fun areItemsTheSame(oldItem: MemoType, newItem: MemoType): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MemoType, newItem: MemoType): Boolean {
        return oldItem == newItem
    }
}