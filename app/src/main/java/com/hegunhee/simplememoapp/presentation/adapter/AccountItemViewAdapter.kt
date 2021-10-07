package com.hegunhee.simplememoapp.presentation.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hegunhee.simplememoapp.R
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.databinding.AccountitemviewBinding

class AccountItemViewAdapter() :
    RecyclerView.Adapter<AccountItemViewAdapter.AccountItemViewHolder>() {

    private var accountList = listOf<accountItemEntity>()

    inner class AccountItemViewHolder(private val binding: AccountitemviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: accountItemEntity) = with(binding) {
            if (item.category == "지출")
                price.setTextColor(Color.RED)
            else
                price.setTextColor(Color.BLUE)

            this.description.text = if (item.description.isNullOrBlank()) {
                this.description.setTextColor(Color.GRAY)
                item.attr
            } else item.description
            this.category.text = item.category
            this.time.text = item.day + "/" + item.time
            this.price.text = item.price.toString()

            this.root.setOnClickListener {
                Log.d("Adapter", item.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountItemViewHolder {
        val view =
            AccountitemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountItemViewHolder, position: Int) {
        holder.bindView(accountList[position])

    }

    override fun getItemCount(): Int = accountList.size

    fun setData(itemList: List<accountItemEntity>) {
        accountList = itemList
    }
}