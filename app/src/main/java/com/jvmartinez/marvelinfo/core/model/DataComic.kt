package com.jvmartinez.marvelinfo.core.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class DataComic(
        @JsonProperty("count") val count: Int,
        @JsonProperty("limit") val limit: Int,
        @JsonProperty("offset") val offset: Int,
        @JsonProperty("results")val results: List<ComicResult>,
        @JsonProperty("total") val total: Int
) : Serializable