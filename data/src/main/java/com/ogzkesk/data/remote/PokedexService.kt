package com.ogzkesk.data.remote

import com.ogzkesk.data.remote.dto.PokemonDTO
import com.ogzkesk.data.remote.dto.PokemonsDTO
import com.ogzkesk.data.remote.dto.SpeciesDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexService {


    @GET("pokemon")
    suspend fun fetchPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ) : PokemonsDTO


    @GET("pokemon-species/{name}")
    suspend fun fetchPokemonInfo(
        @Path("name") name: String
    ): SpeciesDTO

    @GET("pokemon/{name}")
    suspend fun fetchPokemon(
        @Path("name") id: String
    ): PokemonDTO

}