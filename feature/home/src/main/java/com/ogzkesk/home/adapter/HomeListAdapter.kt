package com.ogzkesk.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ogzkesk.core.model.ResultModel
import com.ogzkesk.home.databinding.RvItemCardBinding

class HomeListAdapter : ListAdapter<ResultModel, HomeViewHolder>(DiffUtil) {

    private lateinit var onClick: (ResultModel) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemCardBinding.inflate(inflater, parent, false)
        return HomeViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnClickListener(onClick: (ResultModel) -> Unit) {
        this.onClick = onClick
    }
}