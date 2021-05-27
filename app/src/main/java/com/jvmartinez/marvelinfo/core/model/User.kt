package com.jvmartinez.marvelinfo.core.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class User(
    private var uid: String,
    private var name: String?,
    private var lastName: String?,
    private var email: String?
) : Serializable {

    @Exclude
    fun toMap() : Map<String, Any?>{
        return mapOf(
            "uid" to uid,
            "name" to name,
            "lastName" to lastName,
            "email" to email
        )
    }
}