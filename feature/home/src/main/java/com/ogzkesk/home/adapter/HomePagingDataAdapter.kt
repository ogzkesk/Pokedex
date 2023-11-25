package com.ogzkesk.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.ogzkesk.common.model.ResultModel
import com.ogzkesk.home.databinding.RvItemCardBinding

class HomePagingDataAdapter : PagingDataAdapter<ResultModel, PagingViewHolder>(DiffUtil) {

    private lateinit var onClick: (name: String?) -> Unit
    val footerStateAdapter = FooterStateAdapter { retry() }
    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemCardBinding.inflate(inflater, parent, false)
        return PagingViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    fun setOnItemClickListener(onClick: (String?) -> Unit) {
        this.onClick = onClick
    }

    object DiffUtil : ItemCallback<ResultModel>() {
        override fun areItemsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
            return oldItem == newItem
        }
    }
}