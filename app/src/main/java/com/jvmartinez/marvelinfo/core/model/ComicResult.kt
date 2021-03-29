package com.jvmartinez.marvelinfo.core.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class ComicResult(
        @JsonProperty("description") val description: String?,
        @JsonProperty("events") val events: BaseInfo,
        @JsonProperty("id") val id: Int,
        @JsonProperty("modified") val modified: String,
        @JsonProperty("resourceURI") val resourceURI: String,
        @JsonProperty("series") val series: Series,
        @JsonProperty("stories") val stories: BaseInfo,
        @JsonProperty("thumbnail") val thumbnail: Thumbnail,
        @JsonProperty("urls") val urls: List<Url>,
        @JsonProperty("characters")val characters: BaseInfo,
        @JsonProperty("creators")val creators: BaseInfo,
        @JsonProperty("dates")val dates: List<Date>,
        @JsonProperty("diamondCode")val diamondCode: String,
        @JsonProperty("digitalId")val digitalId: Int,
        @JsonProperty("ean")val ean: String,
        @JsonProperty("format")val format: String,
        @JsonProperty("images")val images: List<Thumbnail>,
        @JsonProperty("isbn")val isbn: String,
        @JsonProperty("issn")val issn: String,
        @JsonProperty("issueNumber")val issueNumber: Int,
        @JsonProperty("pageCount")val pageCount: Int,
        @JsonProperty("prices")val prices: List<Price>,
        @JsonProperty("textObjects")val textObjects: List<TextObject>,
        @JsonProperty("title")val title: String,
        @JsonProperty("upc")val upc: String,
        @JsonProperty("variantDescription")val variantDescription: String,
) : Serializable