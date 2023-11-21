package com.ogzkesk.data.repository

import com.ogzkesk.core.model.PokemonModel
import com.ogzkesk.core.util.Resource
import com.ogzkesk.core.model.PokemonsModel
import com.ogzkesk.data.util.Constants.LIMIT
import com.ogzkesk.data.util.Constants.OFFSET
import kotlinx.coroutines.flow.Flow

interface PokedexRepository {

    fun fetchPokemons(limit: Int, offset: Int): Flow<Resource<PokemonsModel>>

    fun fetchPokemonById(id: Int) : Flow<Resource<PokemonModel>>

}