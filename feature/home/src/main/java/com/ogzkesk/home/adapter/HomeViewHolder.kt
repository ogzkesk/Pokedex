package com.ogzkesk.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.Options
import com.ogzkesk.core.model.ResultModel
import com.ogzkesk.home.databinding.RvItemCardBinding
import okhttp3.internal.notify

class HomeViewHolder(
    private val binding: RvItemCardBinding,
    private val onClick: (ResultModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ResultModel) {
        binding.apply {

            root.setOnClickListener { onClick(item) }
            tvPokemonName.text = item.name
            tvPokemonNo.text = item.no

            ivPokemon.load(item.imgUrl) {
                decoderFactory(SvgDecoder.Factory())
                crossfade(true)
            }
        }
    }
}