package com.hegunhee.newsimplememoapp.ui.memo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hegunhee.newsimplememoapp.data.entity.Memo
import com.hegunhee.newsimplememoapp.databinding.ItemMemoBinding


class MemoAdapter(private var memoList: List<Memo>, val onMemoClick: (Memo) -> Unit) :
    RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

    inner class MemoViewHolder(private val binding: ItemMemoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(memo: Memo) = with(binding) {
            day.text = memo.day.toString()
            dayOfWeek.text = memo.dayOfWeek
            amPm.text = memo.amPm
            hour.text = memo.hour.toString()
            minute.text = memo.minute.toString()
            attr.text = memo.attr
            description.text = if (memo.description.isBlank()) {
                description.setTextColor(Color.GRAY)
                memo.attr
            } else memo.description
            asset.text = memo.asset
            price.text = memo.price.toString()
            if (memo.category == "지출")
                price.setTextColor(Color.RED)
            else
                price.setTextColor(Color.BLUE)

            root.setOnClickListener {
                onMemoClick(memo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bindView(memoList[position])
    }

    override fun getItemCount(): Int = memoList.size

    fun setData(memoList: List<Memo>) {
        this.memoList = memoList
        notifyDataSetChanged()
    }
}