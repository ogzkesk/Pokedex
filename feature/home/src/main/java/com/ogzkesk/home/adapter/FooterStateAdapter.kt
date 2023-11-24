package com.ogzkesk.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.ogzkesk.home.R
import com.ogzkesk.home.databinding.LayoutAppendBinding

class FooterStateAdapter(private val onRetry: () -> Unit) : LoadStateAdapter<FooterStateViewHolder>() {

    override fun onBindViewHolder(holder: FooterStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterStateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutAppendBinding.inflate(inflater,parent,false)
        return FooterStateViewHolder(binding,onRetry)
    }
}