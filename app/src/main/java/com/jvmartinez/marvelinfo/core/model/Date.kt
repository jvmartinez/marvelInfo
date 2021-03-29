package com.jvmartinez.marvelinfo.core.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class Date(
        @JsonProperty("date") val date: String,
        @JsonProperty("type") val type: String
) : Serializable