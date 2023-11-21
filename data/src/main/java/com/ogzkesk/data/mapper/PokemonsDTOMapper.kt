package com.ogzkesk.data.mapper

import com.ogzkesk.core.base.Mapper
import com.ogzkesk.core.ext.capitalize
import com.ogzkesk.core.model.PokemonsModel
import com.ogzkesk.core.model.ResultModel
import com.ogzkesk.data.remote.dto.PokemonsDTO
import com.ogzkesk.data.remote.dto.ResultDTO
import com.ogzkesk.data.util.Constants.MAP_IMAGE_SUFFIX
import com.ogzkesk.data.util.Constants.MAP_IMAGE_URL
import com.ogzkesk.data.util.Constants.MAP_NO_PREFIX
import java.util.Locale
import javax.inject.Inject


class PokemonsDTOMapper @Inject constructor() : Mapper<PokemonsDTO, PokemonsModel> {

    override suspend fun map(input: PokemonsDTO): PokemonsModel {
        return PokemonsModel(
            count = input.count ?: 0,
            next = input.next ?: "",
            previous = input.previous ?: "",
            results = input.results?.map(::mapResult) ?: emptyList()
        )
    }

    private fun mapResult(input: ResultDTO?) : ResultModel {
        return ResultModel(
            name = input?.name?.capitalize() ?: "",
            url = input?.url ?: "",
            imgUrl = mapImageUrl(input?.url),
            no = mapNo(input?.url)
        )
    }

    private fun mapImageUrl(url: String?) : String {
        val id = url?.dropLast(1)?.takeLastWhile { it.isDigit() }
        return buildString {
            append(MAP_IMAGE_URL)
            append(id ?: "")
            append(MAP_IMAGE_SUFFIX)
        }
    }

    private fun mapNo(url:String?) : String {
        val id = url?.dropLast(1)?.takeLastWhile { it.isDigit() }
        val maxLength = 3
        val currentLength = maxLength - (id?.length ?: 0)
        return buildString {
            append(MAP_NO_PREFIX)
            if(id != null){
                (0..currentLength).forEach { _ -> append(0) }
                append(id)
            }
        }
    }
}