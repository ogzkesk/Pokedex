package com.ogzkesk.domain.usecase.home

import com.ogzkesk.core.util.Resource
import com.ogzkesk.core.model.PokemonsModel
import com.ogzkesk.data.repository.PokedexRepository
import com.ogzkesk.data.util.Constants.LIMIT
import com.ogzkesk.data.util.Constants.OFFSET
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchPokemonsUseCase @Inject constructor(private val repository: PokedexRepository) {

    operator fun invoke(limit: Int = LIMIT, offset: Int = OFFSET): Flow<Resource<PokemonsModel>> {
        return repository.fetchPokemons(limit,offset)
    }
}