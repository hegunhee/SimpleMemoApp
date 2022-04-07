package com.hegunhee.newsimplememoapp.ui.statics

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hegunhee.newsimplememoapp.databinding.ItemStaticsBinding
import com.hegunhee.newsimplememoapp.ui.detailStatics.DetailStaticsActivity

class StaticsAdapter : RecyclerView.Adapter<StaticsAdapter.StaticsViewHolder>() {
    private var staticsList = listOf<StaticsData>()

    private lateinit var mContext : Context
    inner class StaticsViewHolder(private val binding : ItemStaticsBinding) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bind(statics : StaticsData) = with(binding){
            this.percent.text = "${statics.percent}%"
            this.attr.text = statics.attr
            this.price.text = "${statics.price}원"

            root.setOnClickListener {
                Intent(mContext,DetailStaticsActivity::class.java).apply {
                    putExtra("statics",statics)
                    mContext.startActivity(this)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaticsViewHolder {
        val binding = ItemStaticsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        mContext = parent.context
        return StaticsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StaticsViewHolder, position: Int) {
        holder.bind(staticsList[position])
    }

    override fun getItemCount(): Int {
        return staticsList.size
    }
    fun setData(list : List<StaticsData>){
        staticsList = list
        notifyDataSetChanged()
    }
}