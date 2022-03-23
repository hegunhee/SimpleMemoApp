package com.hegunhee.newsimplememoapp.ui.memo

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.hegunhee.newsimplememoapp.data.entity.Memo
import com.hegunhee.newsimplememoapp.databinding.ItemMemoBinding
import com.hegunhee.newsimplememoapp.ui.detailMemo.DetailMemo


class MemoAdapter(private var memoList: List<Memo>) : RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

    private lateinit var mContext : Context
    inner class MemoViewHolder(private val binding: ItemMemoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(memo : Memo) = with(binding){
            this.day.text = memo.day.toString()
            this.dayOfWeek.text = memo.dayOfWeek
            this.amPm.text = memo.amPm
            this.hour.text = memo.hour.toString()
            this.minute.text = memo.minute.toString()
            this.attr.text = memo.attr
            this.description.text = if(memo.description.isBlank()){
                this.description.setTextColor(Color.GRAY)
                memo.attr
            } else memo.description
            this.asset.text = memo.asset
            this.price.text = memo.price.toString()
            if(memo.category == "지출")
                price.setTextColor(Color.RED)
            else
                price.setTextColor(Color.BLUE)

            this.root.setOnClickListener {
                val intent = Intent(mContext,DetailMemo::class.java)
                intent.putExtra("Memo",memo)
                mContext.startActivity(intent)
//                Toast.makeText(mContext, memo.toString(), Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        mContext = parent.context
        return MemoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bindView(memoList[position])
    }

    override fun getItemCount(): Int = memoList.size

    fun setData(memoList : List<Memo>){
        this.memoList = memoList
        notifyDataSetChanged()
    }
}