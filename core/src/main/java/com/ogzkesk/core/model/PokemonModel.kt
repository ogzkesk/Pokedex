package com.ogzkesk.core.model

data class PokemonModel(
    val id: Int,
    val height: Int,
    val weight: Int,
    val types: List<String>,
    val abilities: List<String>,
    val stats: List<PokemonStatsModel>,
    val info: String
)
