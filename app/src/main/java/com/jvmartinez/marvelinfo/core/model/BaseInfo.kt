package com.jvmartinez.marvelinfo.core.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class BaseInfo(
    @JsonProperty("available") val available: Int,
    @JsonProperty("collectionURI") val collectionURI: String,
    @JsonProperty("items") val items: List<Item>,
    @JsonProperty("returned") val returned: Int
) : Serializable