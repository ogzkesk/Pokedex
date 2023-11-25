package com.ogzkesk.pokedex.home

import com.ogzkesk.common.model.PokemonModel
import com.ogzkesk.common.model.PokemonsModel
import com.ogzkesk.common.util.Resource
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