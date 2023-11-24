package com.ogzkesk.home.adapter

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.ogzkesk.home.databinding.LayoutAppendBinding

class FooterStateViewHolder(
    private val binding: LayoutAppendBinding,
    private val onRetry: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { onRetry() }
    }

    fun bind(loadState: LoadState) {
        binding.apply {
            progress.isVisible = loadState is LoadState.Loading
            btnRetry.isVisible = loadState is LoadState.Error
            tvError.isVisible = loadState is LoadState.Error

            if (loadState is LoadState.Error) {
                tvError.text = loadState.error.message
            }
        }
    }
}