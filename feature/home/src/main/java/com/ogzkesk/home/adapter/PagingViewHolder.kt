package com.ogzkesk.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.decode.SvgDecoder
import coil.load
import com.ogzkesk.common.model.ResultModel
import com.ogzkesk.home.databinding.RvItemCardBinding

class PagingViewHolder(
    private val binding: RvItemCardBinding,
    private val onClick: (String?) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ResultModel?) {
        binding.apply {
            tvPokemonName.text = item?.name
            tvPokemonNo.text = item?.no
            ivPokemon.load(item?.imgUrl) { decoderFactory(SvgDecoder.Factory()) }
            root.setOnClickListener { onClick(item?.name) }
        }
    }
}