package com.ogzkesk.data.repository

import com.ogzkesk.core.base.Mapper
import com.ogzkesk.core.di.IODispatcher
import com.ogzkesk.core.ext.safeApiCall
import com.ogzkesk.core.model.PokemonModel
import com.ogzkesk.core.model.PokemonsModel
import com.ogzkesk.core.util.Resource
import com.ogzkesk.data.remote.PokedexService
import com.ogzkesk.data.remote.dto.Entry
import com.ogzkesk.data.remote.dto.PokemonDTO
import com.ogzkesk.data.remote.dto.PokemonsDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PokedexRepositoryImpl @Inject constructor(
    private val service: PokedexService,
    private val pokemonsDTOMapper: Mapper<PokemonsDTO,PokemonsModel>,
    private val pokemonDTOMapper: Mapper<PokemonDTO, PokemonModel>,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : PokedexRepository {

    override suspend fun fetchPokemons(pos: Int, pageSize: Int): PokemonsModel {
        return pokemonsDTOMapper(service.fetchPokemons(pos,pageSize))
    }

    override fun fetchPokemonById(id: Int): Flow<Resource<PokemonModel>> {
        return safeApiCall(ioDispatcher) {
            coroutineScope {
                val pokemon = async { service.fetchPokemon(id) }.await()
                val species = async { service.fetchPokemonInfo(id) }.await()
                pokemonDTOMapper(pokemon).copy(info = mapEntries(species.entries))
            }
        }
    }

    private fun mapEntries(entries: List<Entry>?): String {
        return entries?.map { it.text ?: "" }?.random() ?: ""
    }
}