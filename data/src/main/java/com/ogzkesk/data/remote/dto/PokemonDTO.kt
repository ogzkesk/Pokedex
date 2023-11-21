package com.ogzkesk.data.remote.dto


import com.google.gson.annotations.SerializedName

data class PokemonDTO(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("height") val height: Int?,
    @SerializedName("weight") val weight: Int?,
    @SerializedName("stats") val stats: List<Stat>?,
    @SerializedName("types") val types: List<Types>?,
    @SerializedName("abilities") val abilities: List<Ability>?,
)


data class Types(
    @SerializedName("slot") val slot: Int?,
    @SerializedName("type") val type: Type?
)

data class Type(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
)

data class Stat(
    @SerializedName("base_stat") val baseStat: Int?,
    @SerializedName("effort") val effort: Int?,
    @SerializedName("stat") val stat: StatX?
)


data class StatX(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
)

data class Ability(
    @SerializedName("ability") val ability: AbilityX?,
    @SerializedName("is_hidden") val isHidden: Boolean?,
    @SerializedName("slot") val slot: Int?
)

data class AbilityX(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
)