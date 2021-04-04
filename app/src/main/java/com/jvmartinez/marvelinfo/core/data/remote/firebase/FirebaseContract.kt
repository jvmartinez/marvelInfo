package com.jvmartinez.marvelinfo.core.data.remote.firebase

import com.google.firebase.auth.FirebaseUser
import io.reactivex.Observable

interface FirebaseContract {

    fun login(email: String, password: String) : Observable<Boolean>

    fun signUp(user: Map<String, String>) : Observable<Boolean>

    fun currentUser() : Observable<FirebaseUser>
}