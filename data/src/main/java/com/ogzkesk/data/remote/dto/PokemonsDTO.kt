package com.ogzkesk.data.remote.dto


import com.google.gson.annotations.SerializedName

data class PokemonsDTO(
    @SerializedName("count") val count: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<ResultDTO>?
)


data class ResultDTO(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
)