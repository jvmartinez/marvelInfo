package com.jvmartinez.marvelinfo.core.data.remote.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseManager : FirebaseContract {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firebaseFirestore: FirebaseFirestore by lazy {
        Firebase.firestore
    }

    override suspend fun login(email: String, password: String): FirebaseUser? {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await().user
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun signUp(user: Map<String, String>, password: String): Boolean {
        return try {
            val userCreated = firebaseAuth.createUserWithEmailAndPassword(
                user["email"].toString(),
                password
            ).await().user
            userCreated.let { userData ->
                try {
                    firebaseFirestore.collection("User").document(userData.uid).set(user).await()
                    true
                } catch (e: Exception) {
                    false
                }
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun currentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}