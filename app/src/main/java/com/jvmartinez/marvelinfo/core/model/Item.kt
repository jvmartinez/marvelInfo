package com.jvmartinez.marvelinfo.core.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class Item(
    @JsonProperty("name") val name: String,
    @JsonProperty("resourceURI") val resourceURI: String
) : Serializable