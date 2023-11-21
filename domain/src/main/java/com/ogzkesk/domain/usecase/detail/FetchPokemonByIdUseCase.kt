package com.ogzkesk.domain.usecase.detail

import com.ogzkesk.data.repository.PokedexRepository
import javax.inject.Inject

class FetchPokemonByIdUseCase @Inject constructor(private val repository: PokedexRepository) {

    operator fun invoke(id: Int) = repository.fetchPokemonById(id)
}