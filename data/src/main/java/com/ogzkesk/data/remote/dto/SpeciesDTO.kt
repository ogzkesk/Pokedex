package com.ogzkesk.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SpeciesDTO(
    @SerializedName("flavor_text_entries") val entries: List<Entry>?
)

data class Entry(
    @SerializedName("flavor_text") val text: String?
)