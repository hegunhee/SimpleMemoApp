package com.hegunhee.newsimplememoapp.feature.statics

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hegunhee.newsimplememoapp.feature.databinding.ItemStaticsBinding

class StaticsAdapter(private val onStaticsClick: (StaticsData) -> Unit) :
    RecyclerView.Adapter<StaticsAdapter.StaticsViewHolder>() {
    private var staticsList = arrayListOf<StaticsData>()

    inner class StaticsViewHolder(private val binding: ItemStaticsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(statics: StaticsData) = with(binding) {
            this.percent.text = "${statics.percent}%"
            this.attr.text = statics.attr
            this.price.text = "${statics.price}원"

            root.setOnClickListener {
                onStaticsClick(statics)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaticsViewHolder {
        val binding = ItemStaticsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StaticsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StaticsViewHolder, position: Int) {
        holder.bind(staticsList[position])
    }

    override fun getItemCount(): Int {
        return staticsList.size
    }

    fun setData(list: List<StaticsData>) {
        val diffCallBack = StaticsDiffUtil(this.staticsList.toList(), list)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)

        staticsList.run {
            clear()
            addAll(list)
            diffResult.dispatchUpdatesTo(this@StaticsAdapter)
        }
    }
}

class StaticsDiffUtil(private val oldList: List<StaticsData>, private val newList : List<StaticsData>) : DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }


}