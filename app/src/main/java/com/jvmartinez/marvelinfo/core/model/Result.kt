package com.jvmartinez.marvelinfo.core.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class Result(
        @JsonProperty("comics") val comics: BaseInfo,
        @JsonProperty("description") val description: String,
        @JsonProperty("events") val events: BaseInfo,
        @JsonProperty("id") val id: Int,
        @JsonProperty("modified") val modified: String,
        @JsonProperty("name") val name: String,
        @JsonProperty("resourceURI") val resourceURI: String,
        @JsonProperty("series") val series: BaseInfo,
        @JsonProperty("stories") val stories: BaseInfo,
        @JsonProperty("thumbnail") val thumbnail: Thumbnail,
        @JsonProperty("urls") val urls: List<Url>
) : Serializable