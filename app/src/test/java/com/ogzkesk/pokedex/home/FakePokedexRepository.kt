package com.ogzkesk.pokedex.home

import com.ogzkesk.core.model.PokemonModel
import com.ogzkesk.core.model.PokemonsModel
import com.ogzkesk.core.util.Resource
import com.ogzkesk.data.repository.PokedexRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePokedexRepository : PokedexRepository{


    override suspend fun fetchPokemons(pos: Int, pageSize: Int): PokemonsModel {
        return PokemonsModel.initial().copy(results = HomeTestUtils.fakePokemonsData)
    }

    override fun fetchPokemonByName(name: String): Flow<Resource<PokemonModel>> {
        return flow { }
    }
}