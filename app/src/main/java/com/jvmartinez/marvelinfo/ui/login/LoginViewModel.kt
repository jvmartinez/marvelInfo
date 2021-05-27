package com.jvmartinez.marvelinfo.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jvmartinez.marvelinfo.core.data.local.Preferences
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ApiResource
import com.jvmartinez.marvelinfo.core.data.remote.firebase.FirebaseManager
import com.jvmartinez.marvelinfo.utils.MarvelInfoUtils
import kotlinx.coroutines.Dispatchers

class LoginViewModel(
    private val firebaseManager: FirebaseManager,
    private val preferences: Preferences
) : ViewModel() {

    fun login(email: String, password: String) = liveData(Dispatchers.IO) {
        if (MarvelInfoUtils.isEmailValid(email)) {
            try {
                val user = firebaseManager.login(email, password)
                user?.uid.let {
                    preferences.setIsLogin(true)
                    preferences.setDisplayName(user?.displayName.toString())
                    emit(ApiResource.Success(LoginEnum.SUCCESS))
                }
            } catch (e: Exception) {
                emit(ApiResource.Failure(e.message.toString(), e))
            }
        } else {
            emit(ApiResource.Failure("Please enter a valid email address"))
        }
    }

}