package com.ogzkesk.core.model

import androidx.annotation.ColorRes

data class PokemonStatsModel(
    val name: String,
    val power: Int,
    @ColorRes val color: Int
)
