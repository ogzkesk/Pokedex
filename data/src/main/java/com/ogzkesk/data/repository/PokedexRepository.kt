package com.ogzkesk.data.repository

import com.ogzkesk.common.model.PokemonModel
import com.ogzkesk.common.util.Resource
import com.ogzkesk.common.model.PokemonsModel
import kotlinx.coroutines.flow.Flow

interface PokedexRepository {

    suspend fun fetchPokemons(pos: Int, pageSize: Int): PokemonsModel

    fun fetchPokemonByName(name: String) : Flow<Resource<PokemonModel>>

}