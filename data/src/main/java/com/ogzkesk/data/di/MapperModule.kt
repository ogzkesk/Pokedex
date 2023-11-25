package com.ogzkesk.data.di

import com.ogzkesk.common.base.Mapper
import com.ogzkesk.common.model.PokemonModel
import com.ogzkesk.data.mapper.PokemonsDTOMapper
import com.ogzkesk.common.model.PokemonsModel
import com.ogzkesk.data.mapper.PokemonDTOMapper
import com.ogzkesk.data.remote.dto.PokemonDTO
import com.ogzkesk.data.remote.dto.PokemonsDTO
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface MapperModule {

    @Binds
    fun bindPokemonsDTOMapper(
        pokemonsDTOMapper: PokemonsDTOMapper,
    ): Mapper<PokemonsDTO, PokemonsModel>

    @Binds
    fun bindPokemonDTOMapper(
        pokemonDTOMapper: PokemonDTOMapper
    ) : Mapper<PokemonDTO, PokemonModel>

}