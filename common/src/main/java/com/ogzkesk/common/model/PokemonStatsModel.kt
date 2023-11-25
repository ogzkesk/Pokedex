package com.ogzkesk.common.model

import androidx.annotation.ColorRes

data class PokemonStatsModel(
    val name: String,
    val power: Int,
    @ColorRes val color: Int
)
