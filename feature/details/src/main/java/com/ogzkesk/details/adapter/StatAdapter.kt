package com.ogzkesk.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import com.ogzkesk.core.model.PokemonStatsModel
import com.ogzkesk.details.databinding.LayoutStatBinding

class StatAdapter : ListAdapter<PokemonStatsModel, StatViewHolder>(DiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutStatBinding.inflate(inflater, parent, false)
        return StatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DiffUtil : ItemCallback<PokemonStatsModel>() {
        override fun areItemsTheSame(
            oldItem: PokemonStatsModel,
            newItem: PokemonStatsModel,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: PokemonStatsModel,
            newItem: PokemonStatsModel,
        ): Boolean {
            return oldItem == newItem
        }
    }
}