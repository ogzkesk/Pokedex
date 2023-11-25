package com.ogzkesk.data.repository

import com.ogzkesk.core.model.PokemonModel
import com.ogzkesk.core.util.Resource
import com.ogzkesk.core.model.PokemonsModel
import kotlinx.coroutines.flow.Flow

interface PokedexRepository {

    suspend fun fetchPokemons(pos: Int, pageSize: Int): PokemonsModel

    fun fetchPokemonByName(name: String) : Flow<Resource<PokemonModel>>

}