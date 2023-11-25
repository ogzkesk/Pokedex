package com.ogzkesk.common.model

import androidx.annotation.ColorRes

data class PokemonModel(
    val id: Int,
    val name: String,
    val no: String,
    val height: Int,
    val weight: Int,
    val imgUrl: String,
    val abilities: String,
    val types: List<String>,
    val stats: List<PokemonStatsModel>,
    @ColorRes val color: Int,
    val info: String,
)
