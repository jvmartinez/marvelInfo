package com.jvmartinez.marvelinfo.ui.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jvmartinez.marvelinfo.core.data.remote.firebase.FirebaseManager
import kotlinx.coroutines.Dispatchers

class SignUpViewModel(private val firebaseManager: FirebaseManager) : ViewModel() {

    fun signUp(fullName: String, email: String, password: String) = liveData(Dispatchers.IO) {
        emit(firebaseManager.signUp(mutableMapOf("full_name" to fullName, "email" to email), password))
    }
}