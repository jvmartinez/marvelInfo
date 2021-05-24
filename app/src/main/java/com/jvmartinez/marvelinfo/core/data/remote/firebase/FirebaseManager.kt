package com.jvmartinez.marvelinfo.core.data.remote.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jvmartinez.marvelinfo.utils.MarvelTags
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class FirebaseManager : FirebaseContract {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
     private val firebaseDatabase: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference
    }

    override fun login(email: String, password: String) : Observable<Boolean> {
        val subject = BehaviorSubject.create<Boolean>()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            subject.onNext(it.isSuccessful)
            subject.onComplete()
        }.exception.let {
            subject.onError(it)
        }
        return subject
    }

    override fun signUp(user: Map<String, String>) : Observable<Boolean> {
        val subject = BehaviorSubject.create<Boolean>()
        firebaseAuth.createUserWithEmailAndPassword(user["email"].toString(),
                user["password"].toString()).addOnCompleteListener {
            if (it.isSuccessful) {
                firebaseDatabase.database.let { dataBase ->
                    dataBase.reference.child(MarvelTags.DATA_BASE_USER).setValue(user).addOnCompleteListener { resultDataBase ->
                        subject.onNext(resultDataBase.isSuccessful)
                        subject.onComplete()
                    }
                }
            } else {
                subject.onNext(it.isSuccessful)
                subject.onComplete()
            }
        }.exception.let {
            subject.onError(it)
        }
        return subject
    }

    override fun currentUser(): Observable<FirebaseUser> {
        val subject = BehaviorSubject.create<FirebaseUser>()
        if (firebaseAuth.currentUser == null) {
            subject.onError(Exception("UserEmpty"))
        } else {
            subject.onNext(firebaseAuth.currentUser)
            subject.onComplete()
        }
        return subject
    }
}