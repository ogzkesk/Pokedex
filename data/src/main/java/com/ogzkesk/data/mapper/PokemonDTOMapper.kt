package com.ogzkesk.data.mapper

import com.ogzkesk.core.base.Mapper
import com.ogzkesk.core.model.PokemonModel
import com.ogzkesk.core.model.PokemonStatsModel
import com.ogzkesk.data.remote.dto.Ability
import com.ogzkesk.data.remote.dto.PokemonDTO
import com.ogzkesk.data.remote.dto.Stat
import com.ogzkesk.data.remote.dto.Types
import javax.inject.Inject

class PokemonDTOMapper @Inject constructor(): Mapper<PokemonDTO,PokemonModel> {

    override suspend fun map(input: PokemonDTO): PokemonModel {
        return PokemonModel(
            id = input.id ?: 0,
            height = input.height ?: 0,
            weight = input.weight ?: 0,
            types = mapTypes(input.types),
            abilities = mapAbilities(input.abilities),
            stats = mapPokemonStats(input.stats),
            info = ""
        )
    }

    private fun mapTypes(types: List<Types>?) : List<String> {
        return types?.map { it.type?.name ?: "" } ?: emptyList()
    }

    private fun mapAbilities(abilities: List<Ability>?): List<String> {
        return abilities?.map { it.ability?.name ?: "" } ?: emptyList()
    }

    private fun mapPokemonStats(stats: List<Stat>?): List<PokemonStatsModel> {
        return stats?.map {
            PokemonStatsModel(
                name = it.stat?.name ?: "",
                power = it.baseStat ?: 0
            )
        } ?: emptyList()
    }
}