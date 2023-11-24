package com.ogzkesk.data.remote

import com.ogzkesk.data.remote.dto.PokemonDTO
import com.ogzkesk.data.remote.dto.PokemonsDTO
import com.ogzkesk.data.remote.dto.SpeciesDTO
import com.ogzkesk.data.util.Constants.LIMIT
import com.ogzkesk.data.util.Constants.OFFSET
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexService {


    @GET("pokemon")
    suspend fun fetchPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ) : PokemonsDTO


    @GET("pokemon-species/{id}")
    suspend fun fetchPokemonInfo(
        @Path("id") id: Int
    ): SpeciesDTO

    @GET("pokemon/{id}")
    suspend fun fetchPokemon(
        @Path("id") id: Int
    ): PokemonDTO

}