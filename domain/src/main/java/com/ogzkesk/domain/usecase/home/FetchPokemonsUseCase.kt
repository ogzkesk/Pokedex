package com.ogzkesk.domain.usecase.home

import com.ogzkesk.data.repository.PokedexRepository
import javax.inject.Inject

class FetchPokemonsUseCase @Inject constructor(private val repository: PokedexRepository) {

    suspend operator fun invoke(pos:Int, pageSize: Int) = repository.fetchPokemons(pos,pageSize)
}