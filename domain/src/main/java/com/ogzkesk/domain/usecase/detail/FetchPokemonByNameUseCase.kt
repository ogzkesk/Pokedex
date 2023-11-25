package com.ogzkesk.domain.usecase.detail

import com.ogzkesk.data.repository.PokedexRepository
import javax.inject.Inject

class FetchPokemonByNameUseCase @Inject constructor(private val repository: PokedexRepository) {

    operator fun invoke(name: String) = repository.fetchPokemonByName(name)
}