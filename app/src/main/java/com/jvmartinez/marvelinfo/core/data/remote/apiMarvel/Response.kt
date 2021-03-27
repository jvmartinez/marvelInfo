package com.jvmartinez.marvelinfo.core.data.remote.apiMarvel

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.jvmartinez.marvelinfo.core.model.Data
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResponseMarvel(
        @JsonProperty("code") val code: Int,
        @JsonProperty("status") val status: String,
        @JsonProperty("copyright") val copyright: String,
        @JsonProperty("data") val data: Data
) : Serializable