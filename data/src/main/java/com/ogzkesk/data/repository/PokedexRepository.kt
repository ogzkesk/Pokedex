package com.ogzkesk.data.repository

import androidx.paging.PagingData
import com.ogzkesk.core.model.PokemonModel
import com.ogzkesk.core.util.Resource
import com.ogzkesk.core.model.PokemonsModel
import com.ogzkesk.core.model.ResultModel
import com.ogzkesk.data.util.Constants.LIMIT
import com.ogzkesk.data.util.Constants.OFFSET
import kotlinx.coroutines.flow.Flow

interface PokedexRepository {

    suspend fun fetchPokemons(pos: Int, pageSize: Int): PokemonsModel

    fun fetchPokemonById(id: Int) : Flow<Resource<PokemonModel>>

}