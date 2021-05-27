package com.jvmartinez.marvelinfo.core.data.remote.firebase

import com.google.firebase.auth.FirebaseUser

interface FirebaseContract {

    suspend fun login(email: String, password: String): FirebaseUser?

    suspend fun signUp(user: Map<String, String>): Boolean

    suspend fun currentUser(): FirebaseUser?
}