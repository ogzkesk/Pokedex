package com.ogzkesk.core.model


data class PokemonsModel(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<ResultModel>,
) {
    companion object {
        fun initial() : PokemonsModel {
            return PokemonsModel(0,"","", emptyList())
        }
    }
}