package com.ogzkesk.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ogzkesk.core.model.ResultModel

object DiffUtil : DiffUtil.ItemCallback<ResultModel>() {
    override fun areItemsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
        return oldItem == newItem
    }
}