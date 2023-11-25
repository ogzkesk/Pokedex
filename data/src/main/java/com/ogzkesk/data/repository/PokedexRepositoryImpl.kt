package com.ogzkesk.data.repository

import com.ogzkesk.common.base.Mapper
import com.ogzkesk.common.di.IODispatcher
import com.ogzkesk.common.ext.safeApiCall
import com.ogzkesk.common.model.PokemonModel
import com.ogzkesk.common.model.PokemonsModel
import com.ogzkesk.common.util.Resource
import com.ogzkesk.data.remote.PokedexService
import com.ogzkesk.data.remote.dto.Entry
import com.ogzkesk.data.remote.dto.PokemonDTO
import com.ogzkesk.data.remote.dto.PokemonsDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val ENTRY_LANGUAGE = "en"

class PokedexRepositoryImpl @Inject constructor(
    private val service: PokedexService,
    private val pokemonsDTOMapper: Mapper<PokemonsDTO, PokemonsModel>,
    private val pokemonDTOMapper: Mapper<PokemonDTO, PokemonModel>,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : PokedexRepository {

    override suspend fun fetchPokemons(pos: Int, pageSize: Int): PokemonsModel {
        return pokemonsDTOMapper(service.fetchPokemons(pos, pageSize))
    }

    override fun fetchPokemonByName(name: String): Flow<Resource<PokemonModel>> {
        return safeApiCall(ioDispatcher) {
            coroutineScope {
                val pokemon = async { service.fetchPokemon(name) }.await()
                val species = async { service.fetchPokemonInfo(name) }.await()
                pokemonDTOMapper(pokemon).copy(info = mapEntries(species.entries))
            }
        }
    }

    private fun mapEntries(entries: List<Entry>?): String {
        return entries?.filter { it.language.name == ENTRY_LANGUAGE }
            ?.map { it.text }
            ?.random() ?: ""
    }
}