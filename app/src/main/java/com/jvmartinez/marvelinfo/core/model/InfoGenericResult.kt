package com.jvmartinez.marvelinfo.core.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class InfoGenericResult(
        @JsonProperty("description") val description: String?,
        @JsonProperty("id") val id: Int,
        @JsonProperty("modified") val modified: String,
        @JsonProperty("resourceURI") val resourceURI: String,
        @JsonProperty("thumbnail") val thumbnail: Thumbnail,
        @JsonProperty("urls") val urls: List<Url>,
        @JsonProperty("textObjects")val textObjects: List<TextObject>?,
        @JsonProperty("title")val title: String,
) : Serializable