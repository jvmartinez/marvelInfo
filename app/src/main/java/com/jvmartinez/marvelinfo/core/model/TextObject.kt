package com.jvmartinez.marvelinfo.core.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class TextObject(
    @JsonProperty("language") val language: String,
    @JsonProperty("text") val text: String,
    @JsonProperty("type") val type: String
) : Serializable