package com.ogzkesk.details.adapter

import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.RecyclerView
import com.ogzkesk.common.model.PokemonStatsModel
import com.ogzkesk.details.databinding.LayoutStatBinding

class StatViewHolder(private val binding: LayoutStatBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(stat: PokemonStatsModel) {
        binding.apply {

            val color = root.context.getColor(stat.color)

            tvStatDesc.text = stat.name
            tvStat.text = root.context.getString(com.ogzkesk.core.R.string.base_stat,stat.power)
            statProgress.progress = stat.power

            tvStatDesc.setTextColor(color)
            statProgress.setIndicatorColor(color)
            statProgress.trackColor = ColorUtils.setAlphaComponent(color, 40)
        }
    }
}