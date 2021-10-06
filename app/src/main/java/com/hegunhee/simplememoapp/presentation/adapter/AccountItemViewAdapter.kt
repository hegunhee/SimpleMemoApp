package com.hegunhee.simplememoapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.databinding.AccountitemviewBinding

class AccountItemViewAdapter : RecyclerView.Adapter<AccountItemViewAdapter.AccountItemViewHolder>() {

    private var accountList : List<accountItemEntity> = listOf()

    inner class AccountItemViewHolder(private val binding : AccountitemviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(item : accountItemEntity) = with(binding){
            this.description.text = item.description ?: item.attr
            //
            //

            this.root.setOnClickListener {
                // 클릭할시 세부화면으로 이동
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountItemViewHolder {
        val view = AccountitemviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AccountItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountItemViewHolder, position: Int) {
        holder.bindView(accountList[position])

    }

    override fun getItemCount(): Int = accountList.size

    fun setData(itemList : List<accountItemEntity>){
        accountList = itemList
    }
}