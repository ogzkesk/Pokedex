package com.ogzkesk.data.mapper

import androidx.annotation.ColorRes
import com.ogzkesk.common.base.Mapper
import com.ogzkesk.common.ext.capitalize
import com.ogzkesk.common.model.PokemonModel
import com.ogzkesk.common.model.PokemonStatsModel
import com.ogzkesk.common.ui.PokemonColor
import com.ogzkesk.data.remote.dto.Ability
import com.ogzkesk.data.remote.dto.PokemonDTO
import com.ogzkesk.data.remote.dto.Stat
import com.ogzkesk.data.remote.dto.Types
import com.ogzkesk.data.util.Constants
import javax.inject.Inject

class PokemonDTOMapper @Inject constructor() : Mapper<PokemonDTO, PokemonModel> {

    override suspend fun map(input: PokemonDTO): PokemonModel {

        val types = mapTypes(input.types)
        val color = mapColor(types)
        val abilities = mapAbilities(input.abilities)
        val stats = mapPokemonStats(input.stats, color)
        val imgUrl = mapImageUrl(input.id)

        return PokemonModel(
            id = input.id ?: 0,
            no = mapNo(input.id),
            height = input.height ?: 0,
            weight = input.weight ?: 0,
            types = types,
            stats = stats,
            color = color,
            imgUrl = imgUrl,
            abilities = abilities,
            name = input.name?.capitalize() ?: "",
            info = ""
        )
    }

    private fun mapTypes(types: List<Types>?): List<String> {
        return types?.map { it.type?.name ?: "" } ?: emptyList()
    }

    private fun mapAbilities(abilities: List<Ability>?): String {
        val list = abilities?.map { it.ability?.name ?: "" } ?: emptyList()
        return buildString {
            list.forEachIndexed { index, s ->
                append(s.capitalize())
                if (index != 1) {
                    append("\n")
                }
            }
        }
    }

    private fun mapImageUrl(id: Int?): String {
        return buildString {
            append(Constants.MAP_IMAGE_URL)
            append(id ?: 0)
            append(Constants.MAP_IMAGE_SUFFIX)
        }
    }

    private fun mapPokemonStats(stats: List<Stat>?, @ColorRes color: Int): List<PokemonStatsModel> {
        return stats?.map {
            PokemonStatsModel(
                name = mapStatNames(it.stat?.name),
                power = it.baseStat ?: 0,
                color = color
            )
        } ?: emptyList()
    }

    private fun mapStatNames(name: String?): String {
        if (name == null) return ""
        return when (name) {
            Constants.HP -> Constants.MAP_HP
            Constants.ATTACK -> Constants.MAP_ATTACK
            Constants.DEFENSE -> Constants.MAP_DEFENSE
            Constants.SPECIAL_ATTACK -> Constants.MAP_SPECIAL_ATTACK
            Constants.SPECIAL_DEFENSE -> Constants.MAP_SPECIAL_DEFENSE
            Constants.SPEED -> Constants.MAP_SPEED
            else -> ""
        }
    }

    private fun mapNo(id: Int?): String {
        val maxLength = 3
        val currentLength = maxLength - (id?.toString()?.length ?: 0)
        return buildString {
            append(Constants.MAP_NO_PREFIX)
            if (id != null) {
                (0..currentLength).forEach { _ -> append(0) }
                append(id)
            }
        }
    }

    private fun mapColor(types: List<String>?): Int {
        if (types.isNullOrEmpty()) return com.ogzkesk.core.R.color.md_theme_light_primary
        return PokemonColor.values().first {
            it.name == types.first().capitalize()
        }.color
    }
}